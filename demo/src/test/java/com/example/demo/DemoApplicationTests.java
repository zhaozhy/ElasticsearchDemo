package com.example.demo;

import com.example.demo.Interface.GoodsRepository;
import com.example.demo.entity.Goods;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;
    @Test
    public void contextLoads() {
        Goods  goods=new Goods();
        goods.setId(10L);
        goods.setBrandId(1000L);
        goods.setCid1(1L);
        goods.setCid2(2L);
        goods.setCid3(3L);
        goods.setSubtitle("fdsafdsaf");
        goodsRepository.save(goods);

    }

    @Test
    public void contextLoads1() {

        NativeSearchQueryBuilder queryBuilder =new NativeSearchQueryBuilder();

        queryBuilder.withSourceFilter(new FetchSourceFilter(new String []{"id","subtitle","skus"},null));
        queryBuilder.withPageable(PageRequest.of(1,10));

        queryBuilder.withQuery(QueryBuilders.matchQuery("all","key"));

        String categoryAggName="categoryAgg";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        String barandAggName="brandAgg";
        queryBuilder.addAggregation(AggregationBuilders.terms(barandAggName).field("brandId"));
        AggregatedPage<Goods> result=template.queryForPage(queryBuilder.build(),Goods.class);
        long total=result.getTotalElements();
        int totalPages=result.getTotalPages();
        List<Goods> items=result.getContent();
        Aggregations aggregations=result.getAggregations();
        System.out.println(items);
    }

}
