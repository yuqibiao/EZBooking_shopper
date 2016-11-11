package com.yyyu.barbecue.ezbooking_base.callback;

/**
 * 功能：数据提交成功（如登录成功）
 *
 * Created by yyyu on 2016/8/8.
 */
public interface OnDataSubmitListener {
    public void onSuccess(String response);
    public void onFailure(String msg);
}
