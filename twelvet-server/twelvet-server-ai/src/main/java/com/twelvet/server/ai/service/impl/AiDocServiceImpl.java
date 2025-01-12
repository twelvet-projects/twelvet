package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiDoc;
import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.api.ai.domain.AiKnowledge;
import com.twelvet.api.ai.domain.dto.AiDocDTO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.ai.mapper.AiDocMapper;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.mapper.AiKnowledgeMapper;
import com.twelvet.server.ai.mq.RAGChannel;
import com.twelvet.server.ai.mq.consumer.domain.dto.AiDocMqDTO;
import com.twelvet.server.ai.service.IAiDocService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * AI知识库文档Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Service
public class AiDocServiceImpl implements IAiDocService {

	private final AiDocMapper aiDocMapper;

	private final AiDocSliceMapper aiDocSliceMapper;

	private final VectorStore vectorStore;

	private final StreamBridge streamBridge;

	private final AiKnowledgeMapper aiKnowledgeMapper;

	public AiDocServiceImpl(AiDocMapper aiDocMapper, AiDocSliceMapper aiDocSliceMapper, VectorStore vectorStore,
			StreamBridge streamBridge, AiKnowledgeMapper aiKnowledgeMapper) {
		this.aiDocMapper = aiDocMapper;
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.vectorStore = vectorStore;
		this.streamBridge = streamBridge;
		this.aiKnowledgeMapper = aiKnowledgeMapper;
	}

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
	 * @param aiDocDTO AI知识库文档
	 * @return 结果
	 */
	@Override
	public Boolean insertAiDoc(AiDocDTO aiDocDTO) {
		if (RAGEnums.DocSourceTypeEnums.INPUT.equals(aiDocDTO.getSourceType())) { // 处理录入类型数据
			if (StrUtil.isBlank(aiDocDTO.getDocName())) {
				throw new TWTException("文档名称不能为空");
			}

			if (StrUtil.isBlank(aiDocDTO.getContent())) {
				throw new TWTException("文档内容不能为空");
			}
		}
		else if (RAGEnums.DocSourceTypeEnums.UPLOAD.equals(aiDocDTO.getSourceType())) { // 处理上传文件

			if (CollectionUtil.isEmpty(aiDocDTO.getFileList())) {
				throw new TWTException("文件列表不能为空");
			}
		}
		else {
			throw new TWTException("非法来源类型");
		}

		Long knowledgeId = aiDocDTO.getKnowledgeId();
		AiKnowledge aiKnowledge = aiKnowledgeMapper.selectAiKnowledgeByKnowledgeId(knowledgeId);
		if (Objects.isNull(aiKnowledge)) {
			throw new TWTException("不存在此知识库");
		}

		String username = SecurityUtils.getUsername();
		AiDocMqDTO aiDocMqDTO = new AiDocMqDTO();
		aiDocMqDTO.setDocName(aiDocDTO.getDocName());
		aiDocMqDTO.setContent(aiDocDTO.getContent());
		aiDocMqDTO.setKnowledgeId(knowledgeId);
		aiDocMqDTO.setSourceType(aiDocDTO.getSourceType());
		aiDocMqDTO.setFileList(aiDocDTO.getFileList());

		aiDocMqDTO.setOperatorBy(username);
		// streamBridge.send(RAGChannel.ADD_RAG_DOC,
		// MessageBuilder.withPayload(aiDocMqDTO).build());

		return Boolean.TRUE;
	}

	/**
	 * 批量删除AI知识库文档
	 * @param docIds 需要删除的AI知识库文档主键
	 * @return 结果
	 */
	@Override
	public int deleteAiDocByDocIds(Long[] docIds) {
		int i = aiDocMapper.deleteAiDocByDocIds(docIds);

		List<String> vectorIdList = aiDocSliceMapper.selectAiDocSliceVectorIdByDocIds(docIds);
		// 删除向量数据库向量
		if (CollectionUtil.isNotEmpty(vectorIdList)) {
			vectorStore.delete(vectorIdList);
		}

		// 批量删除知识库分片
		aiDocSliceMapper.deleteAiDocSliceByDocIds(docIds);

		return i;
	}

}
