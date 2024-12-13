package com.twelvet.server.ai.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.twelvet.api.ai.domain.AiDoc;
import com.twelvet.server.ai.constant.AIDataSourceConstants;

import java.util.List;

/**
 * AI知识库文档Mapper接口
 *
 * @author TwelveT
 * @WebSite twelvet.cn
 * @date 2024-11-16
 */
@DS(AIDataSourceConstants.DS_MASTER)
public interface AiDocMapper {

	/**
	 * 查询AI知识库文档
	 * @param docId AI知识库文档主键
	 * @return AI知识库文档
	 */
	AiDoc selectAiDocByDocId(Long docId);

	/**
	 * 查询AI知识库文档列表
	 * @param aiDoc AI知识库文档
	 * @return AI知识库文档集合
	 */
	List<AiDoc> selectAiDocList(AiDoc aiDoc);

	/**
	 * 新增AI知识库文档
	 * @param aiDoc AI知识库文档
	 * @return 结果
	 */
	int insertAiDoc(AiDoc aiDoc);

	/**
	 * 修改AI知识库文档
	 * @param aiDoc AI知识库文档
	 * @return 结果
	 */
	int updateAiDoc(AiDoc aiDoc);

	/**
	 * 删除AI知识库文档
	 * @param docId AI知识库文档主键
	 * @return 结果
	 */
	int deleteAiDocByDocId(Long docId);

	/**
	 * 批量删除AI知识库文档
	 * @param docIds 需要删除的数据主键集合
	 * @return 结果
	 */
	int deleteAiDocByDocIds(Long[] docIds);

	/**
	 * 根据知识库ID批量删除AI知识库文档
	 * @param modelIds 需要删除的知识库ID
	 * @return 结果
	 */
	int deleteAiDocByModelIds(Long[] modelIds);

}
