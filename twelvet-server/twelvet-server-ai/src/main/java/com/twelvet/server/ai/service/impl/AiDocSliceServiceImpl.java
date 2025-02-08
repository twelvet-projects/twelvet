package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.api.ai.constant.RAGEnums;
import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.api.ai.domain.dto.AiDocSliceDTO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.service.IAiDocSliceService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * AI知识库文档分片Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Service
public class AiDocSliceServiceImpl implements IAiDocSliceService {

	private final AiDocSliceMapper aiDocSliceMapper;

	private final VectorStore vectorStore;

	public AiDocSliceServiceImpl(AiDocSliceMapper aiDocSliceMapper, VectorStore vectorStore) {
		this.aiDocSliceMapper = aiDocSliceMapper;
		this.vectorStore = vectorStore;
	}

	/**
	 * 查询AI知识库文档分片列表
	 * @param aiDocSlice AI知识库文档分片
	 * @return AI知识库文档分片
	 */
	@Override
	public List<AiDocSlice> selectAiDocSliceList(AiDocSlice aiDocSlice) {
		return aiDocSliceMapper.selectAiDocSliceList(aiDocSlice);
	}

	/**
	 * 修改AI知识库文档分片
	 * @param aiDocSliceDTO AI知识库文档分片
	 * @return 结果
	 */
	@Override
	public int updateAiDocSlice(AiDocSliceDTO aiDocSliceDTO) {
		AiDocSlice aiDocSlice = aiDocSliceMapper.selectAiDocSliceBySliceId(aiDocSliceDTO.getSliceId());

		if (Objects.isNull(aiDocSlice)) {
			throw new TWTException("不存在此分片数据，修改失败");
		}

		// 删除重新添加向量数据
		String vectorId = aiDocSlice.getVectorId();
		vectorStore.delete(Collections.singletonList(vectorId));
		Document document = new Document(aiDocSliceDTO.getContent());
		Map<String, Object> metadata = document.getMetadata();
		metadata.put(RAGEnums.VectorMetadataEnums.KNOWLEDGE_ID.getCode(), aiDocSlice.getKnowledgeId());
		metadata.put(RAGEnums.VectorMetadataEnums.DOC_ID.getCode(), aiDocSlice.getDocId());
		metadata.put(RAGEnums.VectorMetadataEnums.SLICE_ID.getCode(), aiDocSlice.getDocId());
		vectorStore.add(List.of(document));

		aiDocSlice.setContent(aiDocSliceDTO.getContent());
        aiDocSlice.setVectorId(document.getId());

		return aiDocSliceMapper.updateAiDocSlice(aiDocSlice);
	}

}
