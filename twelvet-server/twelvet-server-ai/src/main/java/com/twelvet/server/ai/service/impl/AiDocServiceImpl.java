package com.twelvet.server.ai.service.impl;

import java.util.List;

import com.twelvet.api.ai.domain.AiDoc;
import com.twelvet.server.ai.mapper.AiDocMapper;
import com.twelvet.server.ai.service.IAiDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI知识库文档Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Service
public class AiDocServiceImpl implements IAiDocService {

	@Autowired
	private AiDocMapper aiDocMapper;

	/**
	 * 查询AI知识库文档
	 * @param docId AI知识库文档主键
	 * @return AI知识库文档
	 */
	@Override
	public AiDoc selectAiDocByDocId(Long docId) {
		return aiDocMapper.selectAiDocByDocId(docId);
	}

	/**
	 * 查询AI知识库文档列表
	 * @param aiDoc AI知识库文档
	 * @return AI知识库文档
	 */
	@Override
	public List<AiDoc> selectAiDocList(AiDoc aiDoc) {
		return aiDocMapper.selectAiDocList(aiDoc);
	}

	/**
	 * 新增AI知识库文档
	 * @param aiDoc AI知识库文档
	 * @return 结果
	 */
	@Override
	public int insertAiDoc(AiDoc aiDoc) {
		return aiDocMapper.insertAiDoc(aiDoc);
	}

	/**
	 * 修改AI知识库文档
	 * @param aiDoc AI知识库文档
	 * @return 结果
	 */
	@Override
	public int updateAiDoc(AiDoc aiDoc) {
		return aiDocMapper.updateAiDoc(aiDoc);
	}

	/**
	 * 批量删除AI知识库文档
	 * @param docIds 需要删除的AI知识库文档主键
	 * @return 结果
	 */
	@Override
	public int deleteAiDocByDocIds(Long[] docIds) {
		return aiDocMapper.deleteAiDocByDocIds(docIds);
	}

	/**
	 * 删除AI知识库文档信息
	 * @param docId AI知识库文档主键
	 * @return 结果
	 */
	@Override
	public int deleteAiDocByDocId(Long docId) {
		return aiDocMapper.deleteAiDocByDocId(docId);
	}

}
