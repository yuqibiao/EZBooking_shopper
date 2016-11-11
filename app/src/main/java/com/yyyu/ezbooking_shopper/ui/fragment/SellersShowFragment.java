package com.yyyu.ezbooking_shopper.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.okhttp.Request;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.callback.OnRvItemClickListener;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.fragment.BaseFragment;
import com.yyyu.barbecue.ezbooking_base.ui.widget.RefreshLayout;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.SellersShowAdapter;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerJson;
import com.yyyu.ezbooking_shopper.bean.param.QuerySellerParams;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.ui.activity.SellerDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：商家展示Fragment
 *
 * Created by yyyu on 2016/7/6.
 */
public class SellersShowFragment extends BaseFragment{

    private static final String TAG = "SellersShowFragment";

    @ViewInject(value = R.id.rv_sellers_show)
    private RecyclerView rv_sellers_show;
    @ViewInject(value = R.id.rl_seller_show)
    private RefreshLayout rl_seller_show;

    private QuerySellerParams param;
    private static final int PAGE_SIZE = 10;
    private int currentPage = 1;
    private List<QuerySellerJson.SellerBean> data = new ArrayList<>();
    private SellersShowAdapter adapter;
    private int sellerType = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sllers_show;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if(getArguments()!=null){
            sellerType = getArguments().getInt("param",1);
        }
        param = new QuerySellerParams();
        param.setPageSize(PAGE_SIZE);
        param.setCurrentPage(currentPage);
        param.setSellerType(sellerType);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_sellers_show.setLayoutManager(layoutManager);
        rl_seller_show.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void initListener() {
        rl_seller_show.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(0);
            }
        });
        rl_seller_show.setOnLoadListener(new RefreshLayout.OnLoadListener() {
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

    /**
     * 根据sellerType 刷新数据
     *
     * @param sellerType
     */
    public void refreshDateBySellerType(int sellerType){
        param.setSellerType(sellerType);
        getDataFromNet(0);
    }

    /**
     * 从网络获取数据
     *type == 0 刷新数据
     *type == 1 加载更多
     * @param type
     */
    private void getDataFromNet(final int type) {
        if (type == 0) {
            currentPage = 1;
            param.setCurrentPage(currentPage);
            String paramStr = gson.toJson(param);
            String url = MyHttpUrl.QUERY_SELLER + "data=" + paramStr;

            MyLog.e(TAG , "查看商家+==="+url);

            MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QuerySellerJson>() {
                @Override
                public void onError(Request request, Exception e) {
                    MyLog.e(TAG, e.getMessage());
                    rl_seller_show.setRefreshing(false);
                    rl_seller_show.setLoadFinished();
                }
                @Override
                public void onResponse(QuerySellerJson response) {
                    data = response.RESULT_DATA;
                    adapter = new SellersShowAdapter(mContext, data);
                    rv_sellers_show.setAdapter(adapter);
                    adapter.setOnRvItemClickListener(new OnRvItemClickListener() {
                        @Override
                        public void onRvItemClick(int position) {
                            SellerDetailActivity.startAction(getActivity(),
                                    data.get(position).sellerId );
                        }
                    });
                    rl_seller_show.setRefreshing(false);
                    rl_seller_show.setLoadFinished();
                }
            });
        } else if (type == 1) {
            currentPage++;
            param.setCurrentPage(currentPage);
            String paramStr = gson.toJson(param);
            String url = MyHttpUrl.QUERY_SELLER + "data=" + paramStr;
            MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QuerySellerJson>() {
                @Override
                public void onError(Request request, Exception e) {
                    MyLog.e(TAG, e.getMessage());
                    rl_seller_show.setRefreshing(false);
                    rl_seller_show.setLoadFinished();
                }
                @Override
                public void onResponse(QuerySellerJson response) {
                    if (response.RESULT_DATA == null || response.RESULT_DATA.size() == 0) {
                        //MySnackBar.showShortDef(getActivity(), "没有更多数据了");
                    } else {
                       // MySnackBar.showShortDef(getActivity(), "加载了更多");
                        data.addAll(response.RESULT_DATA);
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                    rl_seller_show.setRefreshing(false);
                    rl_seller_show.setLoadFinished();
                }
            });
        }
    }

    /**
     * 实现Fragment的传参（原来是酱紫写的！！！！不是通过构造方法！！！）
     *
     * @param sellerType
     * @return
     */
    public static SellersShowFragment newInstance(int  sellerType ){
        SellersShowFragment fragment = new SellersShowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("param" , sellerType);
        fragment.setArguments(bundle);
        return fragment;
    }

}
