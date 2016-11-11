package com.yyyu.ezbooking_shopper.bean.json;

import java.util.List;

/**
 * 功能：查询商家服务项的数据json
 *
 * @author yyyu
 * @date 2016/8/27
 */
public class QuerySellerItemJson {

    public int RESULT_CODE ;
    public ResultData1 RESULT_DATA;

    public class ResultData1{
        public List<QuerySellerDetailJson.ServiceItem> ssiLstMap;
    }

}
