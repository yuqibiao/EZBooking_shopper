package com.yyyu.ezbooking_shopper.bean.json;

import java.util.List;

/**
 * 功能：商店详情Json Bean
 *
 * Created by yyyu on 2016/7/18.
 */
public class QuerySellerDetailJson {

    public Integer RESULT_CODE;

    public SellerInfo RESULT_DATA;

    public static class SellerInfo{
        public Integer sellerId ;
        public String sellerType ;
        public String sellerDesc ;
        public String sellerName ;
        public String bigImage ;
        public String mobile ;
        public String sellerAddress ;
        public String lng;
        public String lat;
        public List<ServiceItem> serviceItemList;
        public List<GoodsType>goodsTypeList;
    }

    public static class ServiceItem{
        public Integer serviceItemId;
        public Integer ssiId ;
        public String serviceItemPic;
        public String serviceItemName ;
    }

    public static class GoodsType{
        public Integer goodsTypeId ;
        public String goodsTypeName  ;
        public List<Goods> goodsList;
    }

    public static class Goods{
        public Integer goodsId;
        public double goodsPrice ;
        public String goodsDesc ;
        public String bigImage ;
        public String goodsName;
    }



}
