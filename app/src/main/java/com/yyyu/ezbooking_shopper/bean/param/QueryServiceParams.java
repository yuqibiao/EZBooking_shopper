package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：服务选项对应的数据请求参数
 *
 * @author yyyu
 * @date 2016/8/27
 */
public class QueryServiceParams {

    private String accountId;//---登录用户的id

    public QueryServiceParams(){

    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
