package com.twelvet.server.ai.mapper;

import com.twelvet.api.ai.domain.AiDocSlice;

import java.util.List;

/**
 * AI知识库文档分片Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public interface AiDocSliceMapper {

	/**
	 * 查询AI知识库文档分片
	 * @param sliceId AI知识库文档分片主键
	 * @return AI知识库文档分片
	 */
	AiDocSlice selectAiDocSliceBySliceId(Long sliceId);

	/**
	 * 查询AI知识库文档分片列表
	 * @param aiDocSlice AI知识库文档分片
	 * @return AI知识库文档分片集合
	 */
	List<AiDocSlice> selectAiDocSliceList(AiDocSlice aiDocSlice);

	/**
	 * 新增AI知识库文档分片
	 * @param aiDocSlice AI知识库文档分片
	 * @return 结果
	 */
	int insertAiDocSlice(AiDocSlice aiDocSlice);

	/**
	 * 批量新增AI知识库文档分片
	 * @param aiDocSliceList AI知识库文档分片列表
	 * @return 结果
	 */
	int insertAiDocSliceBatch(List<AiDocSlice> aiDocSliceList);

	/**
	 * 修改AI知识库文档分片
	 * @param aiDocSlice AI知识库文档分片
	 * @return 结果
	 */
	int updateAiDocSlice(AiDocSlice aiDocSlice);

	/**
	 * 删除AI知识库文档分片
	 * @param sliceId AI知识库文档分片主键
	 * @return 结果
	 */
	int deleteAiDocSliceBySliceId(Long sliceId);

	/**
	 * 批量查询AI知识库文档分片对应向量化ID
	 * @param sliceIds 需要查询的数据主键集合
	 * @return 结果
	 */
	List<String> selectAiDocSliceVectorIdBySliceIds(Long[] sliceIds);

	/**
	 * 批量删除AI知识库文档分片
	 * @param sliceIds 需要删除的数据主键集合
	 * @return 结果
	 */
	int deleteAiDocSliceBySliceIds(Long[] sliceIds);

	/**
	 * 根据文档ID批量查询AI知识库文档分片对应向量化ID
	 * @param docIds 需要查询的文档ID
	 * @return 结果
	 */
	List<String> selectAiDocSliceVectorIdByDocIds(Long[] docIds);

	/**
	 * 根据文档ID批量删除AI知识库文档分片
	 * @param docIds 需要删除的文档ID
	 * @return 结果
	 */
	int deleteAiDocSliceByDocIds(Long[] docIds);

	/**
	 * 根据知识库ID批量查询AI知识库文档分片对应向量化ID
	 * @param knowledgeIds 需要查询的知识库ID
	 * @return 结果
	 */
	List<String> selectAiDocSliceVectorIdByKnowledgeIds(Long[] knowledgeIds);

	/**
	 * 根据知识库ID批量删除AI知识库文档分片
	 * @param knowledgeIds 需要删除的知识库ID
	 * @return 结果
	 */
	int deleteAiDocSliceByKnowledgeIds(Long[] knowledgeIds);

}
