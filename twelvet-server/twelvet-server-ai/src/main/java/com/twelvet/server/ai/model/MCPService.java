package com.twelvet.server.ai.model;

import org.springframework.ai.tool.ToolCallback;

/**
 * 大模型MCP服务
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-06-16
 */
public interface MCPService {

	/**
	 * 加载MCP服务
	 * @return List<McpSyncClient>
	 */
	ToolCallback[] loadingMCP();

}
