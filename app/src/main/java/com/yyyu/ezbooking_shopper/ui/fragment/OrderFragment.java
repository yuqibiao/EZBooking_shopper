package com.yyyu.ezbooking_shopper.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.squareup.okhttp.Request;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.fragment.BaseFragment;
import com.yyyu.barbecue.ezbooking_base.ui.widget.RefreshLayout;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.barbecue.ezbooking_base.utils.MyToast;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.OrderCheckAdapter;
import com.yyyu.ezbooking_shopper.bean.json.QueryUserOrderJson;
import com.yyyu.ezbooking_shopper.bean.param.QueryUserOrderParams;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.utils.LogicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 功能：订单界面
 *
 * @author yyyu
 * @date 2016/6/26
 */
public class OrderFragment extends BaseFragment {

    private static final String TAG = "OrderFragment";

    @ViewInject(value = R.id.rv_order_show)
    private RecyclerView rv_order_show;
    @ViewInject(value = R.id.rl_order_show)
    private RefreshLayout rl_order_show;

    private QueryUserOrderParams queryUserOrderParams;
    private int currentPage = 1;
    List<QueryUserOrderJson.Order> orders;
    private OrderCheckAdapter orderCheckAdapter;

    @Override
    public int getLayoutId() {
        return  R.layout.fragment_order;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        EventBus.getDefault().register(this);
        queryUserOrderParams = new QueryUserOrderParams();
        queryUserOrderParams.setPageSize(10);
        queryUserOrderParams.setCurrentPage(currentPage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_order_show.setLayoutManager(layoutManager);
    }

    @Override
    protected void initView() {
        rl_order_show.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void initListener() {
        //---刷新
        rl_order_show.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(0);
            }
        });
        //---加载更多
        rl_order_show.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getDataFromNet(1);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        getDataFromNet(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * EventBus 来自下单完成的Event
     * @param msg
     */
    @Subscribe(threadMode= ThreadMode.MAIN)
    public void checkedOrderFragment(String msg){
        if ("order_confirm".equals(msg)){
           getDataFromNet(0);
        }
    }

    /**
     * type == 0 刷新
     * type == 1 加载更多
     * @param type
     */
    private void getDataFromNet(int type) {
        if(!LogicUtils.getInstance(mContext).isUserLogined()){
            return;
        }
        queryUserOrderParams.setUserId(LogicUtils.getInstance(mContext).getUserId());
        if(type==0){//---刷新
            currentPage=1;
            queryUserOrderParams.setCurrentPage(currentPage);
            String url = MyHttpUrl.QUERY_USER_ORDER+gson.toJson(queryUserOrderParams);
            MyLog.e(TAG , "url==="+url);
            MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QueryUserOrderJson>() {
                @Override
                public void onError(Request request, Exception e) {
                    MyLog.e(TAG , "网络加载异常=="+e.getMessage());
                    MySnackBar.showShortDef(getActivity() ,"网络加载异常！！！");
                    rl_order_show.setLoadFinished();
                }
                @Override
                public void onResponse(QueryUserOrderJson queryUserOrderJson) {
                    orders = queryUserOrderJson.RESULT_DATA;
                    orderCheckAdapter = new OrderCheckAdapter(mContext , orders);
                    rv_order_show.setAdapter(orderCheckAdapter);
                    rl_order_show.setRefreshing(false);
                }
            });
        }else if(type==1){//---加载更多
            currentPage++;
            queryUserOrderParams.setCurrentPage(currentPage);
            String url = MyHttpUrl.QUERY_USER_ORDER+gson.toJson(queryUserOrderParams);
            MyLog.e(TAG , "url==="+url);
            MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QueryUserOrderJson>() {
                @Override
                public void onError(Request request, Exception e) {
                    MyLog.e(TAG , "网络加载异常=="+e.getMessage());
                    MySnackBar.showShortDef(getActivity() ,"网络加载异常！！！");
                    rl_order_show.setLoadFinished();
                }
                @Override
                public void onResponse(QueryUserOrderJson queryUserOrderJson) {
                    List<QueryUserOrderJson.Order> newOrders = queryUserOrderJson.RESULT_DATA;
                    if(newOrders==null ||newOrders.size()==0){
                        MySnackBar.showShortDef(getActivity() ,"没有更多数据了！！！");
                        currentPage--;
                        rl_order_show.setLoadFinished();
                    }else{
                        orders.addAll(newOrders);
                        orderCheckAdapter.setOrders(orders);
                        orderCheckAdapter.notifyDataSetChanged();
                        MySnackBar.showShortDef(getActivity() ,"加载了下一页数据！！！");
                        rl_order_show.setLoadFinished();
                    }
                    MyLog.e(TAG , "response==="+queryUserOrderJson);
                }
            });
        }


    }
}
