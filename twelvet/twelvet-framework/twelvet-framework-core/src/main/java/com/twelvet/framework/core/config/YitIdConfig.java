package com.twelvet.framework.core.config;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.twelvet.framework.core.exception.TWTException;
import jakarta.annotation.PreDestroy;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 分布式雪花算法配置 <a href="https://github.com/yitter/IdGenerator">...</a>
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-12-13
 */
@Order(0)
@ConditionalOnProperty(prefix = "snowflake.enabled", name = "enabled", havingValue = "true", matchIfMissing = false)
public class YitIdConfig implements CommandLineRunner {

	private final static Logger log = LoggerFactory.getLogger(YitIdConfig.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private RedissonClient redissonClient;

	private static final String LOCK_ID_GENERATOR = "LOCK_ID_GENERATOR";

	private static final String WORKER_ID_USED_KEY = "WORKER_ID_USED_KEY_";

	private static final String CACHE_WORKER_ID_PREFIX = "CACHE_WORKER_ID_";

	/**
	 * 机器码位长，决定 WorkerId 的最大值，默认值6，取值范围 [1, 19]，实际上有些语言采用 无符号 ushort (uint16)
	 * 类型接收该参数，所以最大值是16，如果是采用 有符号 short (int16)，则最大值为15
	 */
	private static final int WORKER_ID_BIT_LENGTH = 10;

	/**
	 * 机器码，最重要参数，无默认值，必须 全局唯一（或相同 DataCenterId 内唯一），必须
	 * 程序设定，缺省条件（WorkerIdBitLength取默认值）时最大值63，理论最大值 2^WorkerIdBitLength-1（不同实现语言可能会限定在
	 * 65535 或 32767，原理同 WorkerIdBitLength 规则）。 不同机器或不同应用实例
	 * 不能相同，你可通过应用程序配置该值，也可通过调用外部服务获取值。针对自动注册WorkerId需求，本算法提供默认实现：通过 redis 自动注册 WorkerId
	 * 的动态库，详见“Tools\AutoRegisterWorkerId”
	 */
	private static final int MAX_WORKER_ID = (int) Math.pow(2, WORKER_ID_BIT_LENGTH) - 1;

	/**
	 * 心跳间隔（秒）
	 */
	private static final long HEARTBEAT_INTERVAL = 30;

	/**
	 * 最大重新配置次数
	 */
	private static final int MAX_RECONFIGURE_ATTEMPTS = 3;

	/**
	 * 已重试次数
	 */
	private int reconfigureAttempts = 0;

	/**
	 * 心跳线程
	 */
	private ScheduledExecutorService heartbeatExecutor;

	@Override
	public void run(String... args) throws Exception {
		String ipAddress = getIpAddress();
		log.info("{} 正在配置分布式ID工作机缓存...", ipAddress);
		RLock lock = redissonClient.getLock(LOCK_ID_GENERATOR);
		try {
			lock.lock();
			short workerId = getAvailableWorkerId();
			String cacheKey = CACHE_WORKER_ID_PREFIX + ipAddress;
			redisTemplate.opsForValue().set(cacheKey, String.valueOf(workerId), 240, TimeUnit.SECONDS);
			// 设置雪花算法的工作机 ID
			IdGeneratorOptions options = new IdGeneratorOptions(workerId);
			YitIdHelper.setIdGenerator(options);
			log.info("分布式ID工作机已配置: {} - {}", ipAddress, workerId);
			startHeartbeatTask(ipAddress);
		}
		finally {
			lock.unlock();
			log.info("{} 分布式ID工作机缓存配置完成.", ipAddress);
		}
	}

	/**
	 * 获取本机地址
	 * @return ip地址
	 */
	private String getIpAddress() {
		String localhostStr = NetUtil.getLocalhostStr();
		log.info("================服务ip:{}", localhostStr);
		if (StrUtil.isBlank(localhostStr)) {
			log.error("无法获取本机IP地址.");
			throw new TWTException("无法获取本机IP地址.");
		}
		return localhostStr;
	}

	/**
	 * 获取可用workId
	 * @return workId
	 */
	private short getAvailableWorkerId() {
		for (int i = 1; i <= MAX_WORKER_ID; i++) {
			String workerIdUsedKey = WORKER_ID_USED_KEY + i;
			if (Boolean.FALSE.equals(redisTemplate.hasKey(workerIdUsedKey))) {
				// 设置 worker ID 已使用, 360 秒后过期, 避免 worker ID 未释放
				redisTemplate.opsForValue().set(workerIdUsedKey, "USED", 360, TimeUnit.SECONDS);
				return (short) i;
			}
		}
		throw new TWTException("无可用的 worker ID.");
	}

	/**
	 * 心跳workId
	 * @param ipAddress ipAddress
	 */
	private void startHeartbeatTask(String ipAddress) {
		heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
		heartbeatExecutor.scheduleAtFixedRate(() -> {
			try {
				if (!isValidWorkerId(ipAddress)) {
					log.warn("{} 分布式ID工作机信息失效，重新配置...", ipAddress);
					if (reconfigureAttempts < MAX_RECONFIGURE_ATTEMPTS) {
						reconfigureWorkerId(ipAddress);
					}
					else {
						log.error("{} 重新配置工作机信息失败: 达到最大重新配置次数.", ipAddress);
					}
				}
				else {
					// 续期缓存
					String cacheKey = CACHE_WORKER_ID_PREFIX + ipAddress;
					redisTemplate.expire(cacheKey, 240, TimeUnit.SECONDS);
					String workId = redisTemplate.opsForValue().get(cacheKey);
					redisTemplate.expire(WORKER_ID_USED_KEY + workId, 360, TimeUnit.SECONDS);
					log.info("{} 心跳任务执行成功,workId为{},续期完成", ipAddress, workId);
				}
			}
			catch (Exception e) {
				log.error("心跳任务执行失败: {}", e.getMessage());
			}
		}, 20, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
	}

	/**
	 * 检查workId是否失效
	 * @param ipAddress ip地址
	 * @return boolean
	 */
	private boolean isValidWorkerId(String ipAddress) {
		String cacheKey = CACHE_WORKER_ID_PREFIX + ipAddress;
		return Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey));
	}

	/**
	 * 重新分配workId
	 * @param ipAddress ip地址
	 */
	private void reconfigureWorkerId(String ipAddress) {
		try {
			String cacheKey = CACHE_WORKER_ID_PREFIX + ipAddress;
			redisTemplate.delete(cacheKey);
			// 增加重试次数
			reconfigureAttempts++;
			run();
			// 分配成功后重置允许下次重试次数
			reconfigureAttempts = 0;
		}
		catch (Exception e) {
			log.error("{} 重新配置工作机信息失败: {}", ipAddress, e.getMessage());
		}
	}

	/**
	 * 销毁实例前注销占用的workId
	 */
	@PreDestroy
	public void shutdown() {
		String ipAddress = getIpAddress();
		String cacheKey = CACHE_WORKER_ID_PREFIX + ipAddress;
		String workerUsedKey = WORKER_ID_USED_KEY + redisTemplate.opsForValue().get(cacheKey);
		redisTemplate.delete(workerUsedKey);
		redisTemplate.delete(cacheKey);
		if (heartbeatExecutor != null) {
			heartbeatExecutor.shutdown();
		}
	}

}