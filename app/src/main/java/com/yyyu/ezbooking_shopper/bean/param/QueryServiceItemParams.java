package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：查看商家服务信息的请求参数bean
 *
 * @author yyyu
 * @date 2016/8/27
 */
public class QueryServiceItemParams {

    private String sellerId;

    public  QueryServiceItemParams(){

    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
