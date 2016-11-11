package com.yyyu.barbecue.ezbooking_base.bean.json;

/**
 * 功能：登录/注册 返回的json bean
 * Created by yyyu on 2016/7/27.
 */
public class LoginJson {
    public Integer RESULT_CODE ;
    public UserInfo RESULT_DATA;

    public class UserInfo{
        public Integer userId ;
        public Integer userLevel ;
        public Integer userType ;
        public String userName ;
        public String mobile ;
        public String userImages;
        public String userAddress ;
    }

}
