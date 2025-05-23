package com.twelvet.server.ai.mapper;

import com.twelvet.api.ai.domain.AiMcp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI MCP服务Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-03-31
 */
public interface AiMcpMapper {

	/**
	 * 查询AI MCP服务
	 * @param mcpId AI MCP服务主键
	 * @return AI MCP服务
	 */
	public AiMcp selectAiMcpByMcpId(Long mcpId);

	/**
	 * 查询AI MCP服务列表
	 * @param aiMcp AI MCP服务
	 * @return AI MCP服务集合
	 */
	public List<AiMcp> selectAiMcpList(AiMcp aiMcp);

	/**
	 * 查询AI MCP服务名称是否唯一
	 * @param name 服务名称
	 * @param mcpId mcpId
	 * @return Boolean
	 */
	public Boolean selectAiMcpExist(@Param("name") String name, @Param("mcpId") Long mcpId);

	/**
	 * 新增AI MCP服务
	 * @param aiMcp AI MCP服务
	 * @return 结果
	 */
	public int insertAiMcp(AiMcp aiMcp);

	/**
	 * 修改AI MCP服务
	 * @param aiMcp AI MCP服务
	 * @return 结果
	 */
	public int updateAiMcp(AiMcp aiMcp);

	/**
	 * 删除AI MCP服务
	 * @param mcpId AI MCP服务主键
	 * @return 结果
	 */
	public int deleteAiMcpByMcpId(Long mcpId);

	/**
	 * 批量删除AI MCP服务
	 * @param mcpIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteAiMcpByMcpIds(Long[] mcpIds);

}
