package com.example.demo.service;

import com.example.demo.page.Page;

import java.util.List;
import java.util.Map;

public interface BaseSearchService<T> {

    List<T> query(String keyword,Class<T> tClass);

    List<Map<String,Object>> queryHit(String keyword,String indexName,String ... fieldNames);

    Page<Map<String ,Object>> queryHitByPage(int pageNo,int pageSize,String  keyword,String indexName,String ... fieldNames);

    void deleteIndex(String indexName);
}
