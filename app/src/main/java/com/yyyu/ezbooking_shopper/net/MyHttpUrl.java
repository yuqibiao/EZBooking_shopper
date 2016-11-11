package com.yyyu.ezbooking_shopper.net;

/**
 * 功能：URL地址
 * Created by yyyu on 2016/6/28.
 */
public class MyHttpUrl {

    public static final String IP_PORT = "http://120.25.220.179:8089/";
    public static final String BASE_ADDRESS = IP_PORT + "dbLife/rest/";
    public static final String FILE_BASE = IP_PORT+"dbLife/";
    //登录
    public static final String LOGIN_URL = BASE_ADDRESS + "rest?method=user.login";
    //注册
    //public static final String REGISTER_URL = BASE_ADDRESS+"rest?method=user.login";
    //查询商家
    public static final String QUERY_SELLER = BASE_ADDRESS+"rest?method=user.querySeller&";
    //查询商家详情
    public static final String QUERY_SELLER_DETAIL = BASE_ADDRESS+"rest?method=user.querySellerById&data=";
    //下单
    public static final String TO_CREATE_ORDER = BASE_ADDRESS+"rest?method=user.createOrder&data=";
    //查看用户订单
    public static final String QUERY_USER_ORDER = BASE_ADDRESS+"rest?method=user.queryUserOrder&data=";
    //查看我的信息
    public static final String QUERY_MY_INFO = BASE_ADDRESS+"rest?method=user.queryMyInfo&data=";
    //修改我的信息
    public static final String MODIFY_MY_INFO = BASE_ADDRESS+"rest?method=user.modifyUserInfo&data=";
    //修改用户头像
    public static final String MODIFY_USER_ICON = BASE_ADDRESS+"rest";
    //查看推销的商家
    public static final String QUERY_SERVICE_INFO = BASE_ADDRESS+"rest?method=user.index&data=";
    //查看商家的服务信息
    public static final String QUERY_SERVICE_ITEM=BASE_ADDRESS+"rest?method=user.queryServiceItem&data=";

}
