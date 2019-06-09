package com.example.demo.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SimplePage implements Paginable {
    public static final long serialVersionUID = 1L;
    public static final int DEF_COUNT=20;

    protected  int totalCount=0;
    protected  int pageSize=20;
    protected  int pageNo=1;

   public  SimplePage(int pageNo,int pageSize,int totalCount){
        if(totalCount<=0){
            this .totalCount=0;
        }else {
            this .totalCount=totalCount;
        }

        if(pageSize<=0){
            this .pageSize=DEF_COUNT;
        }else{
            this.pageSize=pageSize;
        }
        if(pageNo<=0){
            this .pageNo=1;
        }else{
            this.pageNo=pageNo;
        }
        if((this.pageNo-1)*this.pageSize>=totalCount){
            this .pageNo=totalCount/pageSize;
            if(this .pageNo==0){
                this.pageNo=1;
            }
        }
   }

    @Override
    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public int getTotalPage() {
       int totalPage=totalCount/pageSize;
       if(totalCount % pageSize !=0 || totalPage==0){
           totalPage++;
       }
        return totalPage;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }


    @Override
    public boolean isFirstPage() {
        return pageNo<=1;
    }

    @Override
    public boolean isLastPage() {
        return pageNo>=getTotalPage();
    }

    @Override
    public int getNextPage() {
       if(isLastPage()){
           return  pageNo;
       }else {
           return   pageNo+1;
       }
    }

    @Override
    public int getPrePage() {
       if(isFirstPage()){
           return  pageNo;
       }else {
           return  pageNo-1;
       }
    }
    public  void  setTotalCount(int totalCount){
       this .totalCount=totalCount;
    }

    public  void  setPageSize(int pageSize){
       this .pageSize=pageSize;
    }
    public void setPageNo(int pageNo){this .pageNo=pageNo;}

    protected  int filterNo;
   public int getFilterNo(){return  filterNo;}
}
