package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.server.ai.domain.AiDoc;
import com.twelvet.server.ai.domain.AiDocSlice;
import com.twelvet.server.ai.domain.dto.AiDocDTO;
import com.twelvet.framework.security.utils.SecurityUtils;
import com.twelvet.framework.utils.DateUtils;
import com.twelvet.server.ai.mapper.AiDocMapper;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.service.IAiDocService;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

	@Autowired
	private AiDocMapper aiDocMapper;

	@Autowired
	private AiDocSliceMapper aiDocSliceMapper;

	@Autowired
	private VectorStore vectorStore;

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
	public int insertAiDoc(AiDocDTO aiDocDTO) {
		Date nowDate = DateUtils.getNowDate();
		String username = SecurityUtils.getUsername();

		AiDoc aiDoc = new AiDoc();
		aiDoc.setDocName(aiDocDTO.getDocName());
		aiDoc.setModelId(aiDocDTO.getModelId());
		aiDoc.setCreateBy(username);
		aiDoc.setCreateTime(nowDate);
		aiDoc.setUpdateTime(nowDate);

		int i = aiDocMapper.insertAiDoc(aiDoc);

		Long modelId = aiDoc.getModelId();
		Long docId = aiDoc.getDocId();

		// https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html#_parameters_4
		TokenTextSplitter splitter = new TokenTextSplitter(800, 350, 5, 10000, Boolean.TRUE);

		Document document = new Document(aiDocDTO.getContent());
		List<Document> docs = splitter.split(document);

		List<AiDocSlice> docSliceArrayList = new ArrayList<>();
		for (Document doc : docs) {
			AiDocSlice aiDocSlice = new AiDocSlice();
			aiDocSlice.setModelId(modelId);
			aiDocSlice.setDocId(docId);
			aiDocSlice.setSliceName(aiDoc.getDocName());
			aiDocSlice.setContent(doc.getContent());
			aiDocSlice.setCreateBy(username);
			aiDocSlice.setCreateTime(nowDate);
			aiDocSlice.setUpdateTime(nowDate);

			docSliceArrayList.add(aiDocSlice);

			Map<String, Object> metadata = doc.getMetadata();
			metadata.put("modelId", modelId);
			metadata.put("docId", docId);
			metadata.put("sliceId", "1");
		}

		if (CollectionUtil.isNotEmpty(docSliceArrayList)) {
			aiDocSliceMapper.insertBatch(docSliceArrayList);
		}

		vectorStore.add(docs);

		return i;
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
		int i = aiDocMapper.deleteAiDocByDocIds(docIds);

		// TODO 删除向量数据库向量
		// vectorStore.add();

		// 批量删除知识库分片
		aiDocSliceMapper.deleteAiDocSliceByDocIds(docIds);

		return i;
	}

}
