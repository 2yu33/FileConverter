package com.wu.ming.service.impl;

import com.wu.ming.pojo.FileSearchDTO;
import com.wu.ming.service.EsService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class EsServiceImpl implements EsService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 分页查询数据
    @Override
    public List<FileSearchDTO> pageFileSearch(int current, int pageSize, String keyword) {
        return doSearch(buildQuery(current, pageSize, keyword));
    }

    /**
     * 构造条件构造器
     * @param keyword 查询关键词
     * @return        条件构造器
     */
    private NativeSearchQueryBuilder buildQuery(int current, int pageSize, String keyword){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        // 查询条件构造
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 看有没有查询条件
        if (StringUtils.hasLength(keyword)){
            boolQueryBuilder
                    .must(QueryBuilders.boolQuery()
                            .should(QueryBuilders.matchQuery("fileName", keyword))
                            .should(QueryBuilders.matchQuery("content", keyword))
                    );
        }
        // 分页条件构造
        PageRequest pageRequest = PageRequest.of(current, pageSize);
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.fieldSort("create_time").order(SortOrder.DESC);

        // 查询
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 分页
        nativeSearchQueryBuilder.withPageable(pageRequest);
        // 排序
        nativeSearchQueryBuilder.withSorts(sortBuilder);

        return nativeSearchQueryBuilder;
    }

    /**
     * 执行搜索
     * @param nativeSearchQueryBuilder 条件构造器
     * @return                         搜索结果
     */
    private List<FileSearchDTO> doSearch(NativeSearchQueryBuilder nativeSearchQueryBuilder){
        // 进行搜索
        try {
            SearchHits<FileSearchDTO> searchHit = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), FileSearchDTO.class);
            List<SearchHit<FileSearchDTO>> searchHits = searchHit.getSearchHits();
            searchHits.forEach(System.out::println);
            return searchHit.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}
