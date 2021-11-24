package com.twelvet.server.es.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.twelvet.api.es.constant.EsConstants;
import com.twelvet.api.es.domain.EsCommon;
import com.twelvet.api.es.domain.dto.EsCommonDTO;
import com.twelvet.api.es.domain.vo.EsCommonVO;
import com.twelvet.framework.core.exception.TWTException;
import com.twelvet.framework.utils.JacksonUtils;
import com.twelvet.framework.utils.StringUtils;
import com.twelvet.server.es.service.EsCommonService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author twelvet
 * <p>
 * ES公共搜素服务
 */
@Service
public class EsCommonServiceImpl implements EsCommonService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 索引
     */
    private static final IndexCoordinates INDEX_COMMON = IndexCoordinates.of(EsConstants.INDEX_TWELVET_COMMON);

    /**
     * 搜索
     *
     * @return EsCommonVO
     */
    @Override
    public EsCommonVO search(EsCommonDTO esCommonDTO) {
        int page = esCommonDTO.getPage();
        int pageSize = esCommonDTO.getPageSize();

        if (page * pageSize > EsConstants.MAX_LIMIT) {
            throw new TWTException("超出ES分页数量1W，如需更多分页请自行实现。");
        }

        // ES分页需要减一
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        // 高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span class='highlighted'>")
                .postTags("</span>")
                // 高亮字段
                .field("title.like");

        // 多条件查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 构造查询条件
        buildFilter(esCommonDTO, boolQueryBuilder);

        // 默认按权重排序
        FieldSortBuilder scoreSort = new FieldSortBuilder("_score").order(SortOrder.DESC);

        // 发起查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder
                // 默认按权重排序,如需更多排序条件可加withSort
                .withSort(scoreSort)
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .withHighlightBuilder(highlightBuilder)
                .build();
        SearchHits<EsCommon> searchHits = elasticsearchRestTemplate.search(
                nativeSearchQuery,
                EsCommon.class,
                INDEX_COMMON
        );

        List<SearchHit<EsCommon>> searchHitList = searchHits.toList();

        List<EsCommon> rows = new ArrayList<>();
        searchHitList.forEach(esCommonSearchHit -> {
            EsCommon content = esCommonSearchHit.getContent();
            List<String> hls = esCommonSearchHit.getHighlightField("title.like");

            // 设置标题高亮
            content.setTitle(StringUtils.join(hls));
            rows.add(content);
        });

        // 总数量
        long total = elasticsearchRestTemplate.count(
                nativeSearchQuery,
                EsCommon.class,
                INDEX_COMMON
        );

        EsCommonVO esCommonVO = new EsCommonVO();
        esCommonVO.setTotal(total);
        esCommonVO.setRows(rows);

        return esCommonVO;
    }

    /**
     * 新增文档
     *
     * @param esCommon EsCommon
     */
    @Override
    public void insert(EsCommon esCommon) {
        String id = esCommon.getId();
        // 不存在数据插入
        boolean exists = elasticsearchRestTemplate.exists(id, INDEX_COMMON);
        if (!exists) {
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withObject(esCommon)
                    .withId(id)
                    .build();
            elasticsearchRestTemplate.index(indexQuery, INDEX_COMMON);
        }
    }

    /**
     * 删除文档
     *
     * @param id id
     */
    @Override
    public void deleteById(String id) {
        elasticsearchRestTemplate.delete(id, INDEX_COMMON);
    }

    /**
     * 更新文档
     *
     * @param esCommon EsCommon
     */
    @Override
    public void updateById(EsCommon esCommon) {
        String id = esCommon.getId();
        byte[] bytes = JacksonUtils.toJsonAsBytes(esCommon);
        Map<String, Object> stringObjectMap = JacksonUtils.readMap(bytes);
        Document document = Document.create();
        document.putAll(stringObjectMap);
        UpdateQuery build = UpdateQuery.builder(id).withDocument(document).build();
        elasticsearchRestTemplate.update(build, INDEX_COMMON);
    }

    /**
     * 拼接查询条件
     *
     * @param esCommonDTO      搜索条件
     * @param boolQueryBuilder 多条件查询
     */
    private void buildFilter(EsCommonDTO esCommonDTO, BoolQueryBuilder boolQueryBuilder) {
        String query = esCommonDTO.getQuery();
        int status = esCommonDTO.getStatus();

        // 分别搜索标题，内容
        boolQueryBuilder
                .should(QueryBuilders.termQuery("title.like", query).boost(100))
                .should(QueryBuilders.termQuery("info", query).boost(10))
                // 至少匹配一次
                .minimumShouldMatch(1);

        // 显示状态
        boolQueryBuilder.must(QueryBuilders.termQuery("status", status));

    }

}
