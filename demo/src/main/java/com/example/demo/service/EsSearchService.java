package com.example.demo.service;

import com.example.demo.entity.ProductDocument;

import java.util.List;

public interface EsSearchService extends BaseSearchService<ProductDocument> {

    void  save(ProductDocument... productDocuments);

    void  delete(String id);


    void  deleteAll();

    ProductDocument getById(String id);
    List<ProductDocument> getAll();
 }
