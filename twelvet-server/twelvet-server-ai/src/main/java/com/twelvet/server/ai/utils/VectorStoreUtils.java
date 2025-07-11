package com.twelvet.server.ai.utils;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.domain.AiModel;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.redis.service.RedisUtils;
import com.twelvet.framework.utils.IdUtils;
import com.twelvet.framework.utils.SpringContextHolder;
import com.twelvet.server.ai.constant.AIDataSourceConstants;
import com.twelvet.server.ai.constant.RAGConstants;
import com.twelvet.server.ai.mapper.AiModelMapper;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 向量数据库工具类 使用单例模式管理VectorStore实例，提供分布式环境下线程安全的向量数据库访问
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-07-11
 */
public class VectorStoreUtils {

	private static final Logger log = LoggerFactory.getLogger(VectorStoreUtils.class);

	/**
	 * VectorStore单例实例，使用volatile确保可见性
	 */
	private static volatile VectorStore vectorStoreInstance;

	/**
	 * 当前实例对应的刷新ID，用于检测分布式环境下的配置变更
	 */
	private static volatile String currentRefreshId;

	/**
	 * 私有构造函数，防止外部实例化
	 */
	private VectorStoreUtils() {
	}

	/**
	 * 获取VectorStore单例实例 使用分布式锁确保在分布式环境下的线程安全 支持基于刷新ID的自动配置更新检测
	 * @return VectorStore实例
	 * @throws TWTException 当创建VectorStore失败时抛出异常
	 */
	public static VectorStore getInstance() {
		// 检查是否需要刷新实例
		if (needsRefresh()) {
			RedissonClient redissonClient = SpringContextHolder.getBean(RedissonClient.class);
			RLock lock = redissonClient.getLock(RAGConstants.LOCK_INIT_VECTOR_STORE);

			try {
				// 尝试获取分布式锁
				if (lock.tryLock()) {
					try {
						// 双重检查，防止在等待锁的过程中其他线程已经处理了刷新
						if (needsRefresh()) {
							log.info("检测到配置变更，正在重新创建VectorStore实例...");
							vectorStoreInstance = createVectorStore();
							String globalRefreshId = getGlobalRefreshId();
							// 成功创建实例后，如果redis全局ID为空需要进行创建
							if (StringUtils.isBlank(globalRefreshId)) {
								// 生成新的全局刷新ID
								globalRefreshId = IdUtils.fastSimpleUUID();

								// 将新的刷新ID存储到Redis，通知所有分布式节点
								setGlobalRefreshId(globalRefreshId);
							}
							// 更新本地刷新ID
							currentRefreshId = globalRefreshId;

							log.info("VectorStore实例重新创建完成，刷新ID: {}", currentRefreshId);
						}
					}
					finally {
						// 确保锁被释放
						if (lock.isHeldByCurrentThread()) {
							lock.unlock();
						}
					}
				}
				else {
					throw new TWTException("获取VectorStore初始化锁超时，请稍后重试");
				}
			}
			catch (Exception e) {
				log.error("VectorStore初始化失败", e);
				throw new TWTException("VectorStore初始化失败");
			}
		}
		return vectorStoreInstance;
	}

	/**
	 * 获取当前VectorStore实例 如果实例不存在，则创建新实例
	 * @return VectorStore实例
	 */
	public static VectorStore getVectorStore() {
		return getInstance();
	}

	/**
	 * 刷新VectorStore实例 重新创建VectorStore实例，用于配置更新后的刷新操作 使用分布式锁确保在分布式环境下的安全刷新
	 * 生成新的全局刷新ID，通知所有分布式节点进行刷新
	 * @throws TWTException 当刷新VectorStore失败时抛出异常
	 */
	public static void refresh() {
		RedissonClient redissonClient = SpringContextHolder.getBean(RedissonClient.class);
		RLock lock = redissonClient.getLock(RAGConstants.LOCK_INIT_VECTOR_STORE);

		try {
			// 尝试获取分布式锁
			if (lock.tryLock()) {
				try {
					log.info("正在刷新VectorStore实例...");

					// 生成新的全局刷新ID
					currentRefreshId = IdUtils.fastSimpleUUID();

					// 将新的刷新ID存储到Redis，通知所有分布式节点
					setGlobalRefreshId(currentRefreshId);
					log.info("已生成新的全局刷新ID并存储到Redis: {}", currentRefreshId);

					// 替换旧实例并更新本地刷新ID
					vectorStoreInstance = createVectorStore();

					log.info("VectorStore实例刷新完成，当前刷新ID: {}", currentRefreshId);
				}
				finally {
					// 确保锁被释放
					if (lock.isHeldByCurrentThread()) {
						lock.unlock();
					}
				}
			}
			else {
				throw new TWTException("获取VectorStore刷新锁超时，请稍后重试");
			}
		}
		catch (Exception e) {
			log.error("刷新VectorStore实例失败", e);
			throw new TWTException("刷新VectorStore实例失败");
		}
	}

	/**
	 * 创建新的VectorStore实例 集成动态数据源和模型获取逻辑
	 * @return 新创建的VectorStore实例
	 * @throws TWTException 当创建失败时抛出异常
	 */
	private static VectorStore createVectorStore() {
		try {
			// 获取动态数据源
			DynamicRoutingDataSource dynamicRoutingDataSource = SpringContextHolder
				.getBean(DynamicRoutingDataSource.class);
			DataSource dataSource = dynamicRoutingDataSource.getDataSource(AIDataSourceConstants.DS_VECTOR);

			// 创建JdbcTemplate
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			// 动态获取AI模型配置
			AiModelMapper aiModelMapper = SpringContextHolder.getBean(AiModelMapper.class);
			AiModel aiModel = aiModelMapper.selectAiModelByModelDefault(ModelEnums.ModelTypeEnums.EMBEDDING);

			if (Objects.isNull(aiModel)) {
				throw new TWTException("未找到默认的嵌入模型配置");
			}

			// 动态创建EmbeddingModel实例
			EmbeddingModel embeddingModel = ModelUtils.modelServiceProviderSelector(aiModel.getModelProvider())
				.getEmbeddingModel(aiModel);

			// 构建并返回PgVectorStore实例
			return PgVectorStore.builder(jdbcTemplate, embeddingModel)
				.dimensions(1536) // 向量维度，默认1536
				.distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE) // 距离计算类型：余弦距离
				.indexType(PgVectorStore.PgIndexType.HNSW) // 索引类型：HNSW
				.initializeSchema(true) // 自动初始化数据库模式
				.schemaName("public") // 数据库模式名称
				.vectorTableName("ai_vector") // 向量表名称
				.maxDocumentBatchSize(10000) // 最大文档批处理大小
				.build();

		}
		catch (Exception e) {
			log.error("创建VectorStore实例失败", e);
			throw new TWTException("创建VectorStore实例失败");
		}
	}

	/**
	 * 检查是否需要刷新VectorStore实例 通过比较本地刷新ID和Redis中的全局刷新ID来判断
	 * @return true如果需要刷新，false否则
	 */
	private static boolean needsRefresh() {
		// 如果实例为空，肯定需要创建
		if (Objects.isNull(vectorStoreInstance)) {
			return true;
		}

		try {
			// 获取Redis中的全局刷新ID
			String globalRefreshId = getGlobalRefreshId();

			// 如果Redis中没有刷新ID，说明是第一次运行，需要初始化
			if (StringUtils.isBlank(globalRefreshId)) {
				return true;
			}

			// 比较本地刷新ID和全局刷新ID（如果实例不为空那么本地ID就不会为空）
			boolean needsRefresh = !globalRefreshId.equals(currentRefreshId);

			if (needsRefresh) {
				log.info("检测到配置变更，本地刷新ID: {}, 全局刷新ID: {}", currentRefreshId, globalRefreshId);
			}

			return needsRefresh;

		}
		catch (Exception e) {
			log.warn("检查刷新状态时发生异常，默认不刷新: {}", e.getMessage());
			return false;
		}
	}

	/**
	 * 获取Redis中的全局刷新ID
	 * @return 全局刷新ID，如果不存在则返回null
	 */
	private static String getGlobalRefreshId() {
		try {
			return RedisUtils.getCacheObject(RAGConstants.VECTOR_STORE_REFRESH_ID_CACHE);
		}
		catch (Exception e) {
			log.warn("获取全局刷新ID失败: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * 强制设置全局刷新ID 主要用于测试或特殊场景
	 * @param refreshId 要设置的刷新ID
	 */
	public static void setGlobalRefreshId(String refreshId) {
		try {
			RedisUtils.setCacheObject(RAGConstants.VECTOR_STORE_REFRESH_ID_CACHE, refreshId);
			log.info("已设置全局刷新ID: {}", refreshId);
		}
		catch (Exception e) {
			log.error("设置全局刷新ID失败", e);
			throw new TWTException("设置全局刷新ID失败");
		}
	}

}
