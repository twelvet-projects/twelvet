package com.twelvet.server.ai.service.impl;

import java.util.List;

import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.server.ai.mapper.AiDocSliceMapper;
import com.twelvet.server.ai.service.IAiDocSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI知识库文档分片Service业务层处理
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@Service
public class AiDocSliceServiceImpl implements IAiDocSliceService {

	@Autowired
	private AiDocSliceMapper aiDocSliceMapper;

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
		// TODO 删除向量数据库向量
		// vectorStore.add();
		return i;
	}

}
