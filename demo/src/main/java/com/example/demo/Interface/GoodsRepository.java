package com.example.demo.Interface;

import com.example.demo.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
