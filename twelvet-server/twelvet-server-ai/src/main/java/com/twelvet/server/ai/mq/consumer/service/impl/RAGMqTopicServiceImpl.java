package com.twelvet.server.ai.mq.consumer.service.impl;

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
import com.twelvet.server.ai.mq.consumer.domain.dto.AiDocMqDTO;
import com.twelvet.server.ai.mq.consumer.service.RAGMqTopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * RAG消息处理服务
 * <p>
 *
 * @since 2025/1/10
 */
@Service
public class RAGMqTopicServiceImpl implements RAGMqTopicService {

	private final static Logger log = LoggerFactory.getLogger(RAGMqTopicServiceImpl.class);

	private final AiDocMapper aiDocMapper;

	private final AiDocSliceMapper aiDocSliceMapper;

	private final VectorStore vectorStore;

	public RAGMqTopicServiceImpl(AiDocMapper aiDocMapper, AiDocSliceMapper aiDocSliceMapper, VectorStore vectorStore) {
		this.aiDocMapper = aiDocMapper;
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.vectorStore = vectorStore;
	}

	/**
	 * 处理添加RAG文档消息
	 * @param message Message<AiDocDTO>
	 */
	@Override
	public void addRAGDocChannel(Message<AiDocMqDTO> message) {
		AiDocMqDTO aiDocMqDTO = message.getPayload();

		LocalDateTime nowDate = LocalDateTime.now();
		String username = aiDocMqDTO.getOperatorBy();
		Long knowledgeId = aiDocMqDTO.getKnowledgeId();
		RAGEnums.DocSourceTypeEnums sourceType = aiDocMqDTO.getSourceType();

		// https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_parameters_4
		TokenTextSplitter splitter = new TokenTextSplitter(800, 350, 5, 10000, Boolean.TRUE);

		if (RAGEnums.DocSourceTypeEnums.INPUT.equals(sourceType)) { // 处理录入类型数据
			if (StrUtil.isBlank(aiDocMqDTO.getDocName())) {
				throw new TWTException("文档名称不能为空");
			}

			if (StrUtil.isBlank(aiDocMqDTO.getContent())) {
				throw new TWTException("文档内容不能为空");
			}
			Document document = new Document(aiDocMqDTO.getContent());
			List<Document> docList = splitter.split(document);

			AiDoc aiDoc = new AiDoc();
			aiDoc.setDocName(aiDocMqDTO.getDocName());
			aiDoc.setKnowledgeId(knowledgeId);
			aiDoc.setSourceType(sourceType);
			aiDoc.setCreateBy(username);
			aiDoc.setCreateTime(nowDate);
			aiDoc.setUpdateBy(username);
			aiDoc.setUpdateTime(nowDate);
			aiDocMapper.insertAiDoc(aiDoc);

			Long docId = aiDoc.getDocId();

			List<AiDocSlice> docSliceList = new ArrayList<>();

			for (Document doc : docList) {
				AiDocSlice aiDocSlice = new AiDocSlice();
				aiDocSlice.setKnowledgeId(knowledgeId);
				aiDocSlice.setVectorId(doc.getId());
				aiDocSlice.setDocId(docId);
				aiDocSlice.setSliceName(aiDoc.getDocName());
				aiDocSlice.setContent(doc.getContent());
				aiDocSlice.setCreateBy(username);
				aiDocSlice.setCreateTime(nowDate);
				aiDocSlice.setUpdateBy(username);
				aiDocSlice.setUpdateTime(nowDate);

				docSliceList.add(aiDocSlice);
			}

			if (CollectionUtil.isNotEmpty(docList)) {

				// 插入切片
				aiDocSliceMapper.insertAiDocSliceBatch(docSliceList);

				// 转换为可采用向量ID获取切片ID的map
				Map<String, Long> docSliceByVectorIdMap = docSliceList.stream()
					.collect(Collectors.toMap(AiDocSlice::getVectorId, AiDocSlice::getSliceId));
				// 增加meta属性
				docList.forEach(doc -> {
					Map<String, Object> metadata = doc.getMetadata();
					metadata.put(RAGEnums.VectorMetadataEnums.KNOWLEDGE_ID.getCode(), knowledgeId);
					metadata.put(RAGEnums.VectorMetadataEnums.DOC_ID.getCode(), docId);
					metadata.put(RAGEnums.VectorMetadataEnums.SLICE_ID.getCode(),
							docSliceByVectorIdMap.get(doc.getId()));
				});
				// 添加向量数据，内部会自动进行向量化处理
				vectorStore.add(docList);
			}

		}
		else if (RAGEnums.DocSourceTypeEnums.UPLOAD.equals(sourceType)) { // 处理上传文件

			if (CollectionUtil.isEmpty(aiDocMqDTO.getFileList())) {
				throw new TWTException("文件列表不能为空");
			}

			List<AiDocDTO.FileDTO> fileList = aiDocMqDTO.getFileList();

			List<AiDocSlice> docSliceList = new ArrayList<>();
			List<Document> docList = new ArrayList<>();
			// 针对文件进行doc插入
			for (AiDocDTO.FileDTO file : fileList) {
				String fileName = file.getFileName();
				String fileUrl = file.getFileUrl();

				TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(fileUrl);
				List<Document> documents = tikaDocumentReader.get();
				if (CollectionUtil.isEmpty(documents)) {
					throw new TWTException("上传文件识别为空，空数据");
				}

				AiDoc aiDoc = new AiDoc();
				aiDoc.setDocName(fileName);
				aiDoc.setKnowledgeId(knowledgeId);
				aiDoc.setSourceType(sourceType);
				aiDoc.setCreateBy(username);
				aiDoc.setCreateTime(nowDate);
				aiDoc.setUpdateBy(username);
				aiDoc.setUpdateTime(nowDate);
				// TODO 需要优化批量插入
				aiDocMapper.insertAiDoc(aiDoc);

				Long docId = aiDoc.getDocId();

				// 切片文档
				for (Document document : documents) {
					List<Document> docs = splitter.split(document);

					for (Document doc : docs) {
						Map<String, Object> metadata = doc.getMetadata();
						metadata.put(RAGEnums.VectorMetadataEnums.KNOWLEDGE_ID.getCode(), knowledgeId);
						metadata.put(RAGEnums.VectorMetadataEnums.DOC_ID.getCode(), docId);

						AiDocSlice aiDocSlice = new AiDocSlice();
						aiDocSlice.setKnowledgeId(knowledgeId);
						aiDocSlice.setVectorId(doc.getId());
						aiDocSlice.setDocId(docId);
						aiDocSlice.setSliceName(fileName);
						aiDocSlice.setContent(doc.getContent());
						aiDocSlice.setCreateBy(username);
						aiDocSlice.setCreateTime(nowDate);
						aiDocSlice.setUpdateBy(username);
						aiDocSlice.setUpdateTime(nowDate);

						docSliceList.add(aiDocSlice);
					}

					// 添加到全局数组待插入
					docList.addAll(docs);
				}
			}

			if (CollectionUtil.isNotEmpty(docSliceList)) {

				// 插入切片
				aiDocSliceMapper.insertAiDocSliceBatch(docSliceList);

				// 转换为可采用向量ID获取切片ID的map
				Map<String, Long> docSliceByVectorIdMap = docSliceList.stream()
					.collect(Collectors.toMap(AiDocSlice::getVectorId, AiDocSlice::getSliceId));
				// 增加meta属性
				docList.forEach(doc -> {
					Map<String, Object> metadata = doc.getMetadata();
					metadata.put(RAGEnums.VectorMetadataEnums.SLICE_ID.getCode(),
							docSliceByVectorIdMap.get(doc.getId()));
				});
				// 添加向量数据，内部会自动进行向量化处理
				vectorStore.add(docList);
			}

		}
		else {
			throw new TWTException("非法来源类型");
		}

	}

}
