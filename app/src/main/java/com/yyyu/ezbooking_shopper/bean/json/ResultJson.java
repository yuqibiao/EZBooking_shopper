package com.yyyu.ezbooking_shopper.bean.json;

/**
 * 功能：一般不带返回信息的请求成功结果
 *
 * @author yyyu
 * @date 2016/7/30
 */
public class ResultJson {

    public int RESULT_CODE ;
    public ResultData RESULT_DATA;

    public static class ResultData{
        public int SUCCESS_CODE ;
        public String SUCCESS_MESSAGE;
    }

}
