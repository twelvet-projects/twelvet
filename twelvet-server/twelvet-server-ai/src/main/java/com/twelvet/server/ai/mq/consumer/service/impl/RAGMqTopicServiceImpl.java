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
import com.twelvet.server.ai.mq.RAGChannel;
import com.twelvet.server.ai.mq.consumer.service.RAGMqTopicService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
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
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addRAGDocChannel(Message<AiDocDTO> message) {
		AiDocDTO aiDocDTO = message.getPayload();

		LocalDateTime nowDate = LocalDateTime.now();
		String username = SecurityUtils.getUsername();
		List<Document> docs = List.of();
		List<AiDocSlice> docSliceList = new ArrayList<>();
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
			docs = splitter.split(document);

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
		}
		else if (RAGEnums.DocSourceTypeEnums.UPLOAD.equals(aiDocDTO.getSourceType())) { // 处理上传文件

			if (CollectionUtil.isEmpty(aiDocDTO.getFileList())) {
				throw new TWTException("文件列表不能为空");
			}

			TikaDocumentReader tikaDocumentReader = new TikaDocumentReader("https://static.twelvet.cn/ai/README_ZH.md");
			List<Document> documents = tikaDocumentReader.get();

		}
		else {
			throw new TWTException("非法来源类型");
		}

		if (CollectionUtil.isNotEmpty(docs)) {
			// 插入切片
			aiDocSliceMapper.insertAiDocSliceBatch(docSliceList);

			// 转换为可采用向量ID获取切片ID的map
			Map<String, Long> docSliceMap = docSliceList.stream()
				.collect(Collectors.toMap(AiDocSlice::getVectorId, AiDocSlice::getSliceId));
			docs.forEach(doc -> {
				Map<String, Object> metadataTemp = doc.getMetadata();
				metadataTemp.put(RAGEnums.VectorMetadataEnums.SLICE_ID.getCode(), docSliceMap.get(doc.getId()));
			});
			// 添加向量数据，内部会自动进行向量化处理
			vectorStore.add(docs);
		}

	}

}
