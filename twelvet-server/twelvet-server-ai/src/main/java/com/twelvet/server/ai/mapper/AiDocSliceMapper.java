package com.twelvet.server.ai.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.twelvet.framework.datasource.support.DataSourceConstants;
import com.twelvet.api.ai.domain.AiDocSlice;

import java.util.List;

/**
 * AI知识库文档分片Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@DS(DataSourceConstants.DS_MASTER)
public interface AiDocSliceMapper {

	/**
	 * 查询AI知识库文档分片
	 * @param sliceId AI知识库文档分片主键
	 * @return AI知识库文档分片
	 */
	public AiDocSlice selectAiDocSliceBySliceId(Long sliceId);

	/**
	 * 查询AI知识库文档分片列表
	 * @param aiDocSlice AI知识库文档分片
	 * @return AI知识库文档分片集合
	 */
	public List<AiDocSlice> selectAiDocSliceList(AiDocSlice aiDocSlice);

	/**
	 * 新增AI知识库文档分片
	 * @param aiDocSlice AI知识库文档分片
	 * @return 结果
	 */
	public int insertAiDocSlice(AiDocSlice aiDocSlice);

	/**
	 * 修改AI知识库文档分片
	 * @param aiDocSlice AI知识库文档分片
	 * @return 结果
	 */
	public int updateAiDocSlice(AiDocSlice aiDocSlice);

	/**
	 * 删除AI知识库文档分片
	 * @param sliceId AI知识库文档分片主键
	 * @return 结果
	 */
	public int deleteAiDocSliceBySliceId(Long sliceId);

	/**
	 * 批量删除AI知识库文档分片
	 * @param sliceIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteAiDocSliceBySliceIds(Long[] sliceIds);

}
