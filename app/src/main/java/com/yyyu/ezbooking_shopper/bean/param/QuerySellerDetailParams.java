package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：查询商店详情参数、
 *
 * Created by yyyu on 2016/7/18.
 */
public class QuerySellerDetailParams {

    private int sellerId;

    public QuerySellerDetailParams(){

    }

    public QuerySellerDetailParams(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

}
