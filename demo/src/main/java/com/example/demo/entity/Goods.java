package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@lombok.Data
@Document(indexName = "goods",type = "docs",shards = 1,replicas = 1)
public class Goods implements Serializable {

    @Id
    private  Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String all;

    @Field(type=FieldType.Keyword,index = false)
    private  String subtitle;

    private  Long brandId;
    private  Long cid1;
    private  Long cid2;
    private  Long cid3;

    private Data createTime;
    private Set<Double> price;

    @Field(type = FieldType.Keyword,index = false)
    private  String skus;
    private Map<String ,Object> specs;
}
