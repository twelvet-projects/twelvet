package com.twelvet.server.ai.service.impl;

import com.twelvet.api.ai.domain.AiMcp;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.ai.mapper.AiMcpMapper;
import com.twelvet.server.ai.service.IAiMcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI MCP服务Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2025-03-31
 */
@Service
public class AiMcpServiceImpl implements IAiMcpService {

	@Autowired
	private AiMcpMapper aiMcpMapper;

	/**
	 * 查询AI MCP服务
	 * @param mcpId AI MCP服务主键
	 * @return AI MCP服务
	 */
	@Override
	public AiMcp selectAiMcpByMcpId(Long mcpId) {
		return aiMcpMapper.selectAiMcpByMcpId(mcpId);
	}

	/**
	 * 查询AI MCP服务列表
	 * @param aiMcp AI MCP服务
	 * @return AI MCP服务
	 */
	@Override
	public List<AiMcp> selectAiMcpList(AiMcp aiMcp) {
		return aiMcpMapper.selectAiMcpList(aiMcp);
	}

	/**
	 * 新增AI MCP服务
	 * @param aiMcp AI MCP服务
	 * @return 结果
	 */
	@Override
	public int insertAiMcp(AiMcp aiMcp) {
		LocalDateTime nowDate = LocalDateTime.now();
		aiMcp.setCreateTime(nowDate);
		aiMcp.setUpdateTime(nowDate);
		String loginUsername = SecurityUtils.getUsername();
		aiMcp.setCreateBy(loginUsername);
		aiMcp.setUpdateBy(loginUsername);
		return aiMcpMapper.insertAiMcp(aiMcp);
	}

	/**
	 * 修改AI MCP服务
	 * @param aiMcp AI MCP服务
	 * @return 结果
	 */
	@Override
	public int updateAiMcp(AiMcp aiMcp) {
		aiMcp.setUpdateTime(LocalDateTime.now());
		String loginUsername = SecurityUtils.getUsername();
		aiMcp.setCreateBy(loginUsername);
		aiMcp.setUpdateBy(loginUsername);
		return aiMcpMapper.updateAiMcp(aiMcp);
	}

	/**
	 * 批量删除AI MCP服务
	 * @param mcpIds 需要删除的AI MCP服务主键
	 * @return 结果
	 */
	@Override
	public int deleteAiMcpByMcpIds(Long[] mcpIds) {
		return aiMcpMapper.deleteAiMcpByMcpIds(mcpIds);
	}

	/**
	 * 删除AI MCP服务信息
	 * @param mcpId AI MCP服务主键
	 * @return 结果
	 */
	@Override
	public int deleteAiMcpByMcpId(Long mcpId) {
		return aiMcpMapper.deleteAiMcpByMcpId(mcpId);
	}

}
