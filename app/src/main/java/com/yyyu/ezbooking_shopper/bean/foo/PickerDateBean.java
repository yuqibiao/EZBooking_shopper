package com.yyyu.ezbooking_shopper.bean.foo;

/**
 * 功能：订单信息选择的时候日期对应的Bean
 *
 *
 * Created by yyyu on 2016/9/14.
 */
public class PickerDateBean {

    /**用来展示给用户看的*/
    private String showStr;
    /**用来提交计算*/
    private String dateStr;

    public PickerDateBean(String dateStr, String showStr) {
        this.dateStr = dateStr;
        this.showStr = showStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getShowStr() {
        return showStr;
    }

    public void setShowStr(String showStr) {
        this.showStr = showStr;
    }
}
