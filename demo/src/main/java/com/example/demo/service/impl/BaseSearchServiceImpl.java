package com.example.demo.service.impl;

import com.example.demo.page.Page;
import com.example.demo.service.BaseSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaseSearchServiceImpl<T> implements BaseSearchService<T> {
    private Logger  log= LoggerFactory.getLogger(getClass());

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public List<T> query(String keyword, Class<T> aClass) {
        SearchQuery searchQuery =new NativeSearchQueryBuilder()
                .withQuery(new SimpleQueryStringBuilder(keyword))
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .build();

        return elasticsearchTemplate.queryForList(searchQuery,aClass);
    }

    @Override
    public List<Map<String, Object>> queryHit(String keyword, String indexName, String... fieldNames) {

        QueryBuilder matchQuery=createQueryBuilder(keyword,fieldNames);
        HighlightBuilder  highlightBuilder=createHighlightBuilder(fieldNames);

        SearchResponse response =elasticsearchTemplate.getClient().prepareSearch(indexName)
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .setSize(10000)
                .get();
        SearchHits hits=response.getHits();

        return getHitList(hits);
    }

    private List<Map<String, Object>> getHitList(SearchHits hits) {
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map;
        for (SearchHit searchHit:hits) {
            map=new HashMap<>();
            map.put("source",searchHit.getSourceAsMap());
            Map<String,Object> hitMap=new HashMap<>();
            searchHit.getHighlightFields().forEach((k,v)->{
                String hight="";
                for (Text text:v.getFragments()) {
                    hight+=text.string();
                    hitMap.put(v.getName(),hight);
                }
            });
            map.put("highlight",hitMap);
            list.add(map);

        }
        return  list;
    }

    private HighlightBuilder createHighlightBuilder(String... fieldNames) {
        HighlightBuilder highlightBuilder=new HighlightBuilder()
                .preTags("<span style='color:red'>")
                .postTags("</span>");

        for (String fieldName:fieldNames) {
            highlightBuilder.field(fieldName);
        }
        return  highlightBuilder;
    }

    private QueryBuilder createQueryBuilder(String keyword, String... fieldNames) {
        return  QueryBuilders.multiMatchQuery(keyword,fieldNames)
                .analyzer("ik_max_word")
                .operator(Operator.OR);

    }

    @Override
    public Page<Map<String, Object>> queryHitByPage(int pageNo, int pageSize, String keyword, String indexName, String... fieldNames) {
        QueryBuilder matchQuery=createQueryBuilder(keyword,fieldNames);
        HighlightBuilder  highlightBuilder=createHighlightBuilder(fieldNames);

        SearchResponse response=elasticsearchTemplate.getClient().prepareSearch(indexName)
                .setQuery(matchQuery)
                .highlighter(highlightBuilder)
                .setFrom((pageNo-1)*pageSize)
                .setSize(pageNo*pageSize)
                .get();
        SearchHits hits=response.getHits();
        Long totalCount=hits.getTotalHits();
        Page<Map<String ,Object>> page=new Page<>(pageNo,pageSize,totalCount.intValue());
        page.setList(getHitList(hits));
        return page;
    }

    @Override
    public void deleteIndex(String indexName) {
        elasticsearchTemplate.deleteIndex(indexName);
    }
}
