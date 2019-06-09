package com.example.demo.service;

import com.example.demo.entity.ProductDocument;

public interface EsSearchService extends BaseSearchService<ProductDocument> {

    void  save(ProductDocument... productDocuments);

    void  delete(String id);


    void  deleteAll();

    ProductDocument getById(String id);
}
