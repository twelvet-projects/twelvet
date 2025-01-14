package com.twelvet.server.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.service.IAiDocSliceService;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

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
	 * 查询AI知识库文档分片
	 * @param sliceId AI知识库文档分片主键
	 * @return AI知识库文档分片
	 */
	@Override
	public AiDocSlice selectAiDocSliceBySliceId(Long sliceId) {
		return aiDocSliceMapper.selectAiDocSliceBySliceId(sliceId);
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
	 * @param aiDocSlice AI知识库文档分片
	 * @return 结果
	 */
	@Override
	public int updateAiDocSlice(AiDocSlice aiDocSlice) {
		return aiDocSliceMapper.updateAiDocSlice(aiDocSlice);
	}

	/**
	 * 批量删除AI知识库文档分片
	 * @param sliceIds 需要删除的AI知识库文档分片主键
	 * @return 结果
	 */
	@Override
	public int deleteAiDocSliceBySliceIds(Long[] sliceIds) {
		int i = aiDocSliceMapper.deleteAiDocSliceBySliceIds(sliceIds);

		List<String> vectorIdList = aiDocSliceMapper.selectAiDocSliceVectorIdBySliceIds(sliceIds);
		// 删除向量数据库向量
		if (CollectionUtil.isNotEmpty(vectorIdList)) {
			vectorStore.delete(vectorIdList);
		}
		return i;
	}

}
