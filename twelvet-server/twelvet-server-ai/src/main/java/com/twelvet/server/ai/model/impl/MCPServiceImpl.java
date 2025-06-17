package com.twelvet.server.ai.model.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.twelvet.api.ai.constant.ModelEnums;
import com.twelvet.api.ai.domain.AiMcp;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.redis.service.RedisUtils;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.server.ai.constant.RAGConstants;
import com.twelvet.server.ai.mapper.AiMcpMapper;
import com.twelvet.server.ai.mapper.AiModelMapper;
import com.twelvet.server.ai.model.MCPService;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 大模型MCP服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-16
 */
@Service
public class MCPServiceImpl implements MCPService {

	private final static Logger log = LoggerFactory.getLogger(MCPServiceImpl.class);

	/**
	 * MCP服务列表
	 */
	private final static Map<String, McpSyncClient> MCP_SYNC_CLIENTS = new ConcurrentHashMap<>();

	/**
	 * MCP需要特殊启动的windows命令
	 */
	private static final List<String> WIN_COMMAND = List.of(ModelEnums.McpCommandEnums.NPX.getCode());

	/**
	 * 获取当前操作系统
	 */
	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

	/**
	 * RedissonClient
	 */
	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 模型配置mapper
	 */
	@Autowired
	private AiModelMapper aiModelMapper;

	/**
	 * MCP服务mapper
	 */
	@Autowired
	private AiMcpMapper aiMcpMapper;

	/**
	 * 加载MCP服务
	 * @return List<McpSyncClient>
	 */
	@PostConstruct
	@Override
	public ToolCallback[] loadingMCP() {
		// 防止并发初始化
		RLock lock = redissonClient.getLock(RAGConstants.LOCK_INIT_AI_MCP);
		lock.lock();
		try {
			List<AiMcp> aiMcpList = RedisUtils.getCacheObject(RAGConstants.MCP_LIST_CACHE);
			if (CollUtil.isEmpty(aiMcpList)) { // 缓存没数据将从数据库获取
				AiMcp aiMcp = new AiMcp();
				aiMcp.setStatusFlag(Boolean.TRUE);
				aiMcpList = aiMcpMapper.selectAiMcpList(aiMcp);
				RedisUtils.setCacheObject(RAGConstants.MCP_LIST_CACHE, aiMcpList);
			}
			Map<String, String> dbMcpMap = aiMcpList.stream().collect(Collectors.toMap(AiMcp::getName, AiMcp::getName));
			for (String mcpName : MCP_SYNC_CLIENTS.keySet()) { // 移除已经失效的MCP
				Boolean checkMCPFlag = Boolean.FALSE;
				if (!dbMcpMap.containsKey(mcpName)) { // 数据库已经不存在的需要关闭并移除
					checkMCPFlag = Boolean.TRUE;
				}
				else if (!MCP_SYNC_CLIENTS.get(mcpName).getClientInfo().version().equals(dbMcpMap.get(mcpName))) { // 版本不一致也需要重新初始化
					// TODO 需要实现修改过参数重新进行初始化
					// checkMCPFlag = Boolean.TRUE;
				}

				if (checkMCPFlag) {
					// 移除
					McpSyncClient removemMcpSyncClient = MCP_SYNC_CLIENTS.remove(mcpName);
					// 关闭mcp
					removemMcpSyncClient.close();
					log.info("移除并关闭MCP：{}", removemMcpSyncClient.getClientInfo().name());
				}
			}

			for (AiMcp mcp : aiMcpList) {
				if (MCP_SYNC_CLIENTS.containsKey(mcp.getName())) { // 已经存在的
					continue;
				}
				// 基本信息
				McpSchema.Implementation clientInfo = new McpSchema.Implementation(mcp.getName(), "1.0.0");

				McpClientTransport ClientTransport;
				ModelEnums.McpTypeEnums mcpType = mcp.getMcpType();
				if (ModelEnums.McpTypeEnums.SSE.equals(mcpType)) { // SSE模式
					ClientTransport = HttpClientSseClientTransport.builder(mcp.getSseBaseUrl())
						.sseEndpoint(mcp.getSseEndpoint())
						.build();
				}
				else { // STDIO模式
					List<String> args = new ArrayList<>();
					if (StrUtil.isNotBlank(mcp.getArgs())) {
						args = Arrays.stream(mcp.getArgs().split("\n")).toList();
					}
					String envStr = mcp.getEnv();
					Map<String, String> envMap = new HashMap<>();
					if (StrUtil.isNotBlank(envStr)) {
						try {
							envMap = JacksonUtils.readMap(envStr, String.class, String.class);
						}
						catch (Exception e) {
							throw new TWTException(String.format("MCP服务【%s】,环境变量配置错误", mcp.getName()));
						}
					}

					String command = mcp.getCommand().getCode();
					ServerParameters serverParameters;
					if (OS_NAME.contains("win") && WIN_COMMAND.contains(command)) { // windows下执行bat文件需要特殊处理
						List<String> winArgs = new LinkedList<>(Arrays.asList("/c", command));
						winArgs.addAll(args);
						serverParameters = ServerParameters.builder("cmd.exe").args(winArgs).env(envMap).build();
					}
					else {
						// 启动参数
						serverParameters = ServerParameters.builder(command).args(args).env(envMap).build();
					}

					// 转换器
					ClientTransport = new StdioClientTransport(serverParameters);
				}

				McpSyncClient syncClient = McpClient.sync(ClientTransport)
					.clientInfo(clientInfo)
					.requestTimeout(Duration.ofSeconds(20))
					// 日志
					.loggingConsumer(notification -> {
						log.info("Received log message: {}", notification.data());
					})
					.build();
				// 初始化
				syncClient.initialize();

				// 加入Spring AI
				MCP_SYNC_CLIENTS.put(syncClient.getClientInfo().name(), syncClient);
				log.info("成功初始化MCP：{}，工具列表：{}", syncClient.getClientInfo().name(), syncClient.listTools());
			}

			return new SyncMcpToolCallbackProvider(MCP_SYNC_CLIENTS.values().stream().toList()).getToolCallbacks();
		}
		catch (Exception e) {
			log.error("MCP初始化失败", e);
			throw new TWTException("MCP初始化失败");
		}
		finally {
			lock.unlock();
		}
	}

}
