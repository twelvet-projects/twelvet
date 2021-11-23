package com.twelvet.server.es.service.impl;

import com.twelvet.api.es.domain.vo.EsCommonVO;
import com.twelvet.server.es.service.EsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * <p>
 * ES公共搜素服务
 */
@Service
public class EsCommonServiceImpl implements EsCommonService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Override
    public EsCommonVO search() {
        return null;
    }
}
