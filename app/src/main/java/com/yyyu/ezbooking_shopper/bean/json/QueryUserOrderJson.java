package com.yyyu.ezbooking_shopper.bean.json;

import java.util.List;

/**
 * 功能：查看用户订单的Json Bean
 *
 * @author yyyu
 * @date 2016/8/1
 */
public class QueryUserOrderJson {

    public int RESULT_CODE ;
    public List<Order> RESULT_DATA;

    public static  class Order{
        public int orderId ;
        public String orderNo ;
        public int sellerId ;
        public String sellerName ;
        public String sellerImage ;
        public String orderDesc ;
        public int isPay ;
        public int orderType ;
        public int orderStatus ;
        public String orderDate ;
        public String orderTime ;
        public int personCount ;
        public int deskCount ;
        public double orderPrice ;
        public List<SubOrder> subOrderList;
    }

    public static class SubOrder{
        public int subOrderId ;
        public int goodsId ;
        public int goodsCount ;
        public String goodsName ;
    }

}
