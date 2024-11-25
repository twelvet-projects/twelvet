package com.twelvet.server.ai.service;

import com.twelvet.server.ai.domain.AiDocSlice;

import java.util.List;

/**
 * AI知识库文档分片Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public interface IAiDocSliceService {

	/**
	 * 查询AI知识库文档分片
	 * @param sliceId AI知识库文档分片主键
	 * @return AI知识库文档分片
	 */
	public AiDocSlice selectAiDocSliceBySliceId(Long sliceId);

	/**
	 * 查询AI知识库文档分片列表
	 * @param aiDocSlice AI知识库文档分片
	 * @return AI知识库文档分片集合
	 */
	public List<AiDocSlice> selectAiDocSliceList(AiDocSlice aiDocSlice);

	/**
	 * 修改AI知识库文档分片
	 * @param aiDocSlice AI知识库文档分片
	 * @return 结果
	 */
	public int updateAiDocSlice(AiDocSlice aiDocSlice);

	/**
	 * 批量删除AI知识库文档分片
	 * @param sliceIds 需要删除的AI知识库文档分片主键集合
	 * @return 结果
	 */
	public int deleteAiDocSliceBySliceIds(Long[] sliceIds);

}
