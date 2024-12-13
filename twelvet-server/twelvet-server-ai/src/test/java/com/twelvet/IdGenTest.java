package com.twelvet;

import com.twelvet.server.ai.AiApplication;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * 分布式ID生成测试
 * <p>
 *
 * @since 2024/12/13
 */
@SpringBootTest(classes = AiApplication.class)
public class IdGenTest {

	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 工作ID获取测试
	 */
	@Test
	public void getWorkIdTest() {
		RAtomicLong atomicLong = redissonClient.getAtomicLong("twelvet_work_id");

		// Try to acquire a unique workerId
		long workerId = atomicLong.incrementAndGet(); // Get the next workerId

		// Set expiration time to avoid indefinite lock
		atomicLong.expire(3600 * 1000L, java.util.concurrent.TimeUnit.MILLISECONDS);

		System.out.println(workerId);

	}

	public static void main(String[] args) {
		int s = (int) (Math.pow(2, 10) - 1);
		System.out.println(s);
	}

}
