package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：查询Seller的参数
 * Created by yyyu on 2016/7/6.
 */
public class QuerySellerParams {

    private String sellerName ;
    private Integer sellerType ;
    private Integer pageSize ;
    private Integer currentPage ;

    public QuerySellerParams(){

    }

    public QuerySellerParams(String sellerName, Integer sellerType, Integer pageSize, Integer currentPage) {
        this.sellerName = sellerName;
        this.sellerType = sellerType;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getSellerType() {
        return sellerType;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
