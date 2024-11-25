package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.AiDoc;
import com.twelvet.api.ai.domain.dto.AiDocDTO;

import java.util.List;

/**
 * AI知识库文档Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public interface IAiDocService {

	/**
	 * 查询AI知识库文档
	 * @param docId AI知识库文档主键
	 * @return AI知识库文档
	 */
	public AiDoc selectAiDocByDocId(Long docId);

	/**
	 * 查询AI知识库文档列表
	 * @param aiDoc AI知识库文档
	 * @return AI知识库文档集合
	 */
	public List<AiDoc> selectAiDocList(AiDoc aiDoc);

	/**
	 * 新增AI知识库文档
	 * @param aiDocDTO AI知识库文档
	 * @return 结果
	 */
	public int insertAiDoc(AiDocDTO aiDocDTO);

	/**
	 * 修改AI知识库文档
	 * @param aiDoc AI知识库文档
	 * @return 结果
	 */
	public int updateAiDoc(AiDoc aiDoc);

	/**
	 * 批量删除AI知识库文档
	 * @param docIds 需要删除的AI知识库文档主键集合
	 * @return 结果
	 */
	public int deleteAiDocByDocIds(Long[] docIds);

}
