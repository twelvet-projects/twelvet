package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiDoc;
import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.api.ai.domain.dto.AiDocDTO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.server.ai.mapper.AiDocMapper;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.service.IAiDocService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public AiDocServiceImpl(AiDocMapper aiDocMapper, AiDocSliceMapper aiDocSliceMapper, VectorStore vectorStore) {
		this.aiDocMapper = aiDocMapper;
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.vectorStore = vectorStore;
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
		LocalDateTime nowDate = LocalDateTime.now();
		String username = SecurityUtils.getUsername();

		if (RAGEnums.DocSourceTypeEnums.INPUT.equals(aiDocDTO.getSourceType())) { // 处理录入类型数据
			if (StrUtil.isBlank(aiDocDTO.getDocName())) {
				throw new TWTException("文档名称不能为空");
			}

			if (StrUtil.isBlank(aiDocDTO.getContent())) {
				throw new TWTException("文档内容不能为空");
			}

			AiDoc aiDoc = new AiDoc();
			aiDoc.setDocName(aiDocDTO.getDocName());
			aiDoc.setKnowledgeId(aiDocDTO.getKnowledgeId());
			aiDoc.setSourceType(RAGEnums.DocSourceTypeEnums.INPUT);
			aiDoc.setCreateBy(username);
			aiDoc.setCreateTime(nowDate);
			aiDoc.setUpdateBy(username);
			aiDoc.setUpdateTime(nowDate);

			aiDocMapper.insertAiDoc(aiDoc);

			Long knowledgeId = aiDoc.getKnowledgeId();
			Long docId = aiDoc.getDocId();

			// https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_parameters_4
			TokenTextSplitter splitter = new TokenTextSplitter(800, 350, 5, 10000, Boolean.TRUE);

			Map<String, Object> metadata = new HashMap<>();
			metadata.put(RAGEnums.VectorMetadataEnums.KNOWLEDGE_ID.getCode(), aiDocDTO.getKnowledgeId());
			metadata.put(RAGEnums.VectorMetadataEnums.DOC_ID.getCode(), aiDoc.getDocId());

			Document document = new Document(aiDocDTO.getContent(), metadata);
			List<Document> docs = splitter.split(document);

			List<AiDocSlice> docSliceList = new ArrayList<>();
			for (Document doc : docs) {
				AiDocSlice aiDocSlice = new AiDocSlice();
				aiDocSlice.setKnowledgeId(knowledgeId);
				aiDocSlice.setDocId(docId);
				aiDocSlice.setVectorId(doc.getId());
				aiDocSlice.setSliceName(aiDoc.getDocName());
				aiDocSlice.setContent(doc.getContent());
				aiDocSlice.setCreateBy(username);
				aiDocSlice.setCreateTime(nowDate);
				aiDocSlice.setUpdateBy(username);
				aiDocSlice.setUpdateTime(nowDate);

				docSliceList.add(aiDocSlice);
			}

			if (CollectionUtil.isNotEmpty(docSliceList)) {
				// 插入切片
				aiDocSliceMapper.insertAiDocSliceBatch(docSliceList);

				// TODO 插入向量保存切片ID
				vectorStore.add(docs);
			}
		}
		else if (RAGEnums.DocSourceTypeEnums.UPLOAD.equals(aiDocDTO.getSourceType())) { // 处理上传文件
			TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("https://static.twelvet.cn/ai/README_ZH.md");
			List<Document> documents = tikaDocumentReader.get();

			// TODO 发送MQ进行处理插入
			AiDoc aiDoc = new AiDoc();
			aiDoc.setDocName(aiDocDTO.getDocName());
			aiDoc.setKnowledgeId(aiDocDTO.getKnowledgeId());
			aiDoc.setSourceType(RAGEnums.DocSourceTypeEnums.UPLOAD);
			aiDoc.setCreateBy(username);
			aiDoc.setCreateTime(nowDate);
			aiDoc.setUpdateBy(username);
			aiDoc.setUpdateTime(nowDate);

		}
		else {
			throw new TWTException("非法来源类型");
		}

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
