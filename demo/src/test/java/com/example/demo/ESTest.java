package com.example.demo;

import com.example.demo.Interface.ProductDocumentRepository;
import com.example.demo.entity.Goods;
import com.example.demo.entity.ProductDocument;
import com.example.demo.entity.ProductDocumentBuilder;
import com.example.demo.page.Page;
import com.example.demo.service.EsSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {



    @Autowired
    private ProductDocumentRepository   productDocumentRepository;

    @Autowired
    private EsSearchService esSearchService;
    @Test
    public void contextLoads1() {

        List<ProductDocument>  ps =new ArrayList<ProductDocument>();
        for (Integer i=1;i<=10;i++){

           ProductDocument  productDocument= ProductDocumentBuilder.create().addId("0000"+i.toString()).addProductName("小米手机").addCreateTime(new Date()).addUpdateTime(new Date()).builder();
          // ps.add(productDocument);
            esSearchService.save(productDocument);
        }
       // System.out.println(esSearchService.getAll());

    }

    @Test
    public void contextLoads2() {

     /*   List<ProductDocument>  ps =new ArrayList<ProductDocument>();
        for (Integer i=1;i<=10;i++){

            ProductDocument  productDocument= ProductDocumentBuilder.create().addId("0000"+i.toString()).addProductName("小米手机").addCreateTime(new Date()).addUpdateTime(new Date()).builder();
            // ps.add(productDocument);
            esSearchService.save(productDocument);
        }*/
        //System.out.println(esSearchService.getAll());
        Page<Map<String, Object>> pageList = esSearchService.queryHitByPage(1, 20, "小米手机", "orders", "productName","Id");
        System.out.println(pageList.getList());
    }

}
