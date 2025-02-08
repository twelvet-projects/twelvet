package com.twelvet.server.ai.service;

import com.twelvet.api.ai.domain.AiDocSlice;
import com.twelvet.api.ai.domain.dto.AiDocSliceDTO;

import java.util.List;

/**
 * AI知识库文档分片Service接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
public interface IAiDocSliceService {

	/**
	 * 查询AI知识库文档分片列表
	 * @param aiDocSlice AI知识库文档分片
	 * @return AI知识库文档分片集合
	 */
	public List<AiDocSlice> selectAiDocSliceList(AiDocSlice aiDocSlice);

	/**
	 * 修改AI知识库文档分片
	 * @param aiDocSliceDTO AI知识库文档分片
	 * @return 结果
	 */
	public int updateAiDocSlice(AiDocSliceDTO aiDocSliceDTO);

}
