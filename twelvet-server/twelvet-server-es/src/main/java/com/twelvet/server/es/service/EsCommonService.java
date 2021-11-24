package com.twelvet.server.es.service;

import com.twelvet.api.es.domain.EsCommon;
import com.twelvet.api.es.domain.dto.EsCommonDTO;
import com.twelvet.api.es.domain.vo.EsCommonVO;

/**
 * @author twelvet
 * <p>
 * ES公共搜素服务
 */
public interface EsCommonService {

    /**
     * 搜索
     *
     * @param esCommonDTO EsCommonDTO
     * @return EsCommonVO
     */
    EsCommonVO search(EsCommonDTO esCommonDTO);

    /**
     * 插入文档
     * @param esCommon EsCommon
     */
    void insert(EsCommon esCommon);

    /**
     * 删除文档
     * @param id id
     */
    void deleteById(String id);

    /**
     * 更新文档
     * @param esCommon EsCommon
     */
    void updateById(EsCommon esCommon);

}
