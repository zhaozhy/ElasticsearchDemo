package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.util.Date;
@Data
@Document(indexName = "orders",type="product")
public class ProductDocument implements Serializable {

    @org.springframework.data.annotation.Id
    private  String Id;
    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private  String productName;
    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private  String productDesc;
    private Date createTime;
    private Date updateTime;



}
