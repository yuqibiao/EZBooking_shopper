package com.yyyu.ezbooking_shopper.bean.json;

import java.util.List;

/**
 * 功能：查询我的信息数据JsonBean
 *
 * Created by yyyu on 2016/8/2.
 */
public class QueryMyInfoJson {

    public int RESULT_CODE ;

    public MyInfo RESULT_DATA;

    public static  class MyInfo{
        public int userId ;
        public int userLevel;
        public String userSex ;
        public String userName ;
        public int userType ;
        public String mobile ;
        public String userImages ;
        public String  userAddress ;
        public List<Seller> sellerList;

    }

    public static class Seller{
        public int sellerId ;
        public String sellerName;
        public String bigImage;
        public String mobile ;
    }

}
