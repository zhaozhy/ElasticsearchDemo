package com.example.demo.page;

import java.io.Serializable;
import java.util.List;

public class Page<T> extends SimplePage implements Serializable,Paginable {

    private List<T> list;

    public List<T> getList(){
        return  this .list;
    }
    public void setList(List<T> list){
        this .list=list;
    }

    public  Page(){

    }
    public Page(int pageNo,int pageSize,int totalCount){
        super(pageNo,pageSize,totalCount);
    }

    public Page(int pageNo,int pageSize,int totalCount,List<T> list){
        super(pageNo,pageSize,totalCount);
        this .list=list;
    }
}
