package com.yyyu.ezbooking_shopper.bean.json;

import java.util.List;

/**
 * 功能：
 * Created by yyyu on 2016/7/6.
 */
public class QuerySellerJson {

    public Integer RESULT_CODE ;

    public List<SellerBean> RESULT_DATA;

    public class SellerBean{
        public Integer sellerId ;
        public Integer sellerType ;
        public String sellerName ;
        public String sellerDesc ;
        public String bigImage ;
        public String sellerAddress;
        public String mobile ;
        public String lng ;
        public String lat;
    }

}
