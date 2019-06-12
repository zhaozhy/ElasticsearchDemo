package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.Interface.ProductDocumentRepository;
import com.example.demo.entity.ProductDocument;
import com.example.demo.service.EsSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class EsSearchServiceImpl extends  BaseSearchServiceImpl<ProductDocument> implements EsSearchService {
   private Logger logger= LoggerFactory.getLogger(getClass());
   @Resource
   private ElasticsearchTemplate elasticsearchTemplate;
   @Resource
   private ProductDocumentRepository productDocumentRepository;
    @Override
    public void save(ProductDocument... productDocuments) {
         elasticsearchTemplate.putMapping(ProductDocument.class);
         if(productDocuments.length>0){
          logger.info("【保存索引】:{}", JSON.toJSONString(productDocumentRepository.saveAll(Arrays.asList(productDocuments))));
         }
    }

    @Override
    public void delete(String id) {
        productDocumentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        productDocumentRepository.deleteAll();
    }

    @Override
    public ProductDocument getById(String id) {

        return  productDocumentRepository.findById(id).get();
    }

    @Override
    public List<ProductDocument> getAll() {
        List<ProductDocument> list =new ArrayList<>();
        productDocumentRepository.findAll().forEach(list::add);
        return list;
    }
}
