package com.yyyu.ezbooking_shopper.bean.json;

import java.util.List;

/**
 * 功能：查看服务信息的返回bean
 *
 * @author yyyu
 * @date 2016/8/27
 */
public class QueryServiceInfoJson {

    public int RESULT_CODE ;

    public SellerInfoList RESULT_DATA;

    public class SellerInfoList{

       public  List<SellerInfo> sellerList;
       public  List<SellerInfo> advertList;

    }

    public class SellerInfo{
        public int sellerId ;
        public int sellerType ;
        public String sellerDesc ;
        public String sellerName ;
        public String bigImage;
        public String sellerAddress;
        public String mobile ;
    }


}
