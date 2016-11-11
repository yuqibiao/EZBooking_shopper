package com.yyyu.ezbooking_shopper.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yyyu.barbecue.ezbooking_base.annotate.OnClick;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.callback.OnRvItemClickListener;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseActivity;
import com.yyyu.barbecue.ezbooking_base.ui.widget.RefreshLayout;
import com.yyyu.barbecue.ezbooking_base.ui.widget.loading.ShapeLoadingDialog;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.SellersShowAdapter;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerJson;
import com.yyyu.ezbooking_shopper.bean.param.QuerySellerParams;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：搜索Activity
 *
 * Created by yyyu on 2016/7/6.
 */
public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    @ViewInject(value = R.id.rl_search_all)
    private RefreshLayout rl_search_all;
    @ViewInject(value = R.id.rv_search_all)
    private RecyclerView rv_search_all;
    @ViewInject(value = R.id.et_seller_name)
    private EditText et_seller_name;

    private static final int PAGE_SIZE = 10;
    private int currentPage = 1;
    private QuerySellerParams param;
    private Gson gson;
    private SellersShowAdapter adapter;
    private List<QuerySellerJson.SellerBean> data = new ArrayList<>();
    private ShapeLoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    protected void beforeInit() {
        gson = new Gson();
        param = new QuerySellerParams();
        param.setPageSize(PAGE_SIZE);
        param.setCurrentPage(currentPage);
        loadingDialog = new ShapeLoadingDialog(this);
        loadingDialog.setLoadingText("正在搜索中......");
    }

    @Override
    protected void initView() {
        rl_search_all.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_search_all.setLayoutManager(layoutManager);
    }

    @Override
    protected void initListener() {
        rl_search_all.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getDataFromNet(1);
            }
        });
        rl_search_all.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromNet(0);
            }
        });
    }

    @OnClick({R.id.btn_search, R.id.ib_search_back})
    public void onInjectClick(View v) {
        switch (v.getId()) {
            case R.id.ib_search_back:
                this.finish();
                break;
            case R.id.btn_search: //--search
                param.setSellerName(et_seller_name.getText().toString());
                getDataFromNet(0);
                break;
        }
    }

    /**
     * 从网络获取数据
     *
     * @param type
     */
    private void getDataFromNet(final int type) {
        loadingDialog.show();
        if (type == 0) {
            currentPage = 1;
            param.setCurrentPage(currentPage);
            String paramStr = gson.toJson(param);
            String url = MyHttpUrl.QUERY_SELLER + "data=" + paramStr;

            MyLog.e(TAG, "url===" + url);

            MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QuerySellerJson>() {
                @Override
                public void onError(Request request, Exception e) {
                    MyLog.e(TAG, e.getMessage());
                    MySnackBar.showLongDef(rv_search_all, "网络连接出错");
                    rl_search_all.setRefreshing(false);
                    loadingDialog.dismiss();
                }

                @Override
                public void onResponse(QuerySellerJson response) {
                    data = response.RESULT_DATA;
                    adapter = new SellersShowAdapter(SearchActivity.this, data);
                    adapter.setOnRvItemClickListener(new OnRvItemClickListener() {
                        @Override
                        public void onRvItemClick(int position) {
                            SellerDetailActivity.startAction(SearchActivity.this, data.get(position).sellerId);
                            SearchActivity.this.finish();
                        }
                    });
                    rl_search_all.setRefreshing(false);
                    rv_search_all.setAdapter(adapter);
                    rl_search_all.setLoadFinished();
                    loadingDialog.dismiss();
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
                    MySnackBar.showLongDef(rv_search_all, "网络连接出错");
                    loadingDialog.dismiss();
                }

                @Override
                public void onResponse(QuerySellerJson response) {
                    if (response.RESULT_DATA == null || response.RESULT_DATA.size() == 0) {
                        MySnackBar.showShortDef(rv_search_all, "没有更多数据了");
                    } else {
                        MySnackBar.showShortDef(rv_search_all, "加载了更多");
                        data.addAll(response.RESULT_DATA);
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                    rl_search_all.setLoadFinished();
                    loadingDialog.dismiss();
                }
            });
        }
    }

    /**
     * 跳转到SearchActivity
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

}
