package com.yyyu.ezbooking_shopper.bean.param;

/**
 * 功能：查询用户订单的参数
 *
 * @author yyyu
 * @date 2016/8/1
 */
public class QueryUserOrderParams {

    private int userId;
    private int pageSize;
    private int currentPage;

    public QueryUserOrderParams(){

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

}
