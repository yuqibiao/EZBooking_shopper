package com.yyyu.ezbooking_shopper.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.fragment.BaseFragment;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ViewpagerLooper;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyToast;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.ServiceItemAdapter;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerDetailJson;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerItemJson;
import com.yyyu.ezbooking_shopper.bean.json.QueryServiceInfoJson;
import com.yyyu.ezbooking_shopper.bean.param.QueryServiceItemParams;
import com.yyyu.ezbooking_shopper.bean.param.QueryServiceParams;
import com.yyyu.ezbooking_shopper.bean.param.ToConfirmOrderParams;
import com.yyyu.ezbooking_shopper.callback.OnPickerFinishedListener;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.ui.activity.OrderConfirmActivity;
import com.yyyu.ezbooking_shopper.ui.activity.SellerDetailActivity;
import com.yyyu.ezbooking_shopper.ui.dialog.DateTimePickerDialog;
import com.yyyu.ezbooking_shopper.utils.LogicUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：服务界面
 *
 * @author yyyu
 * @date 2016/6/26
 */
public class ServiceFragment extends BaseFragment {

    private static final String TAG = "ServiceFragment";

    public static final int TO_CAPTURE = 0;

    @ViewInject(value = R.id.ib_scan)
    private ImageButton ib_scan;
    @ViewInject(R.id.ll_seller)
    private LinearLayout ll_seller;
    @ViewInject(R.id.tv_time_choice)
    private TextView tv_time_choice;
    @ViewInject(R.id.vp_adver)
    private ViewPager vp_adver;
    @ViewInject (R.id.vpl_adver)
    private ViewpagerLooper vpl_adver;
    @ViewInject(R.id.tv_adver)
    private TextView tv_adver;
    @ViewInject(R.id.rv_service_item)
    private RecyclerView rv_service_item;

    private QueryServiceParams queryServiceParams;
    private QueryServiceItemParams itemParams;
    private List<QueryServiceInfoJson.SellerInfo> adverList;
    private List<QueryServiceInfoJson.SellerInfo>sellerList;
    private DateTimePickerDialog dateTimePickerDialog;
    private ToConfirmOrderParams toCreateOrderParams;
    private  QueryServiceInfoJson.SellerInfo sellerInfo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void beforeInit() {
        queryServiceParams = new QueryServiceParams();
        itemParams = new QueryServiceItemParams();
        dateTimePickerDialog = new DateTimePickerDialog(mContext);
        toCreateOrderParams = new ToConfirmOrderParams();
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_service_item.setLayoutManager(layoutManager);
    }


    @Override
    protected void initListener() {
        //---点击扫描二维码
        ib_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, TO_CAPTURE);
            }
        });
        //---时段选择
        tv_time_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.e(TAG , "tv_time_choice===onClick");
                dateTimePickerDialog.show();
            }
        });
        //---时段选择完成回调
        dateTimePickerDialog.setOnPickerFinishedListener(new OnPickerFinishedListener() {
            @Override
            public void onPickerFinished() {
                if (sellerInfo == null){
                    return;
                }
                tv_time_choice.setText(dateTimePickerDialog.getInfo());
                toCreateOrderParams.setUserId(LogicUtils.getInstance(mContext).getUserId());
                toCreateOrderParams.setSellerId(sellerInfo.sellerId);
                toCreateOrderParams.setOrderDate(dateTimePickerDialog.getDate());
                toCreateOrderParams.setOrderTime(dateTimePickerDialog.getTime());
                toCreateOrderParams.setPersonCount(Integer.parseInt(dateTimePickerDialog.getPersonNum()));
                toCreateOrderParams.setDeskCount(Integer.parseInt(dateTimePickerDialog.getTableNum()));
                OrderConfirmActivity.startAction(getActivity() , gson.toJson(toCreateOrderParams) , sellerInfo.sellerName , sellerInfo.mobile , 1);
            }
        });
        //---设置广告标题
        vpl_adver.setOnPagerChangedListener(new ViewpagerLooper.OnPagerChangedListener() {
            @Override
            public void onPagerChanged(int position) {
                tv_adver.setText(adverList.get(position).sellerName);
            }
        });
    }

    @Override
    protected void initData() {
        if(!LogicUtils.getInstance(mContext).isUserLogined()){
            return ;
        }
        queryServiceParams.setAccountId(""+LogicUtils.getInstance(mContext).getUserId());
        //---获取推销的商家信息
        String url = MyHttpUrl.QUERY_SERVICE_INFO + gson.toJson(queryServiceParams);
       // MyLog.e(TAG , "url==="+url);
        MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QueryServiceInfoJson>() {
            @Override
            public void onError(Request request, Exception e) {
                MyLog.e(TAG , "网络连接失败=="+e.getMessage());
            }
            @Override
            public void onResponse(QueryServiceInfoJson queryServiceInfoJson) {
                adverList = queryServiceInfoJson.RESULT_DATA.advertList;
                sellerList = queryServiceInfoJson.RESULT_DATA.sellerList;
                if(adverList!=null && adverList.size()>0){
                    tv_adver.setText(adverList.get(0).sellerName);
                }
                //---广告
                vpl_adver.setViewPager(vp_adver, new ViewpagerLooper.LooperPagerAdapter<QueryServiceInfoJson.SellerInfo>
                        (mContext , adverList ,R.layout.vp_item_adver) {
                    @Override
                    protected void setView(View viewItem , final QueryServiceInfoJson.SellerInfo dataBean) {
                        ImageView iv_adver = (ImageView) viewItem.findViewById(R.id.iv_adver);
                        MyBitmapUtils.getInstance(mContext).display(iv_adver ,dataBean.bigImage );
                        iv_adver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SellerDetailActivity.startAction(getActivity() , dataBean.sellerId );
                            }
                        });
                    }
                });

                //---推荐商家(已有更好的解决方案 Base中的AdapterLinearLayout)
                for (int i = 0; i< sellerList.size() ; i++) {
                    QueryServiceInfoJson.SellerInfo bean = sellerList.get(i);
                    View sellerItem = LayoutInflater.from(mContext).inflate(R.layout.gv_item_service_seller , null);
                    CircleImageView civ_seller_icon = (CircleImageView) sellerItem.findViewById(R.id.civ_seller_icon);
                    TextView cb_seller_name = (TextView) sellerItem.findViewById(R.id.cb_seller_name);
                    TextView tv_seller_tel = (TextView) sellerItem.findViewById(R.id.tv_seller_tel);
                    MyBitmapUtils.getInstance(mContext).display(civ_seller_icon , MyHttpUrl.FILE_BASE+bean.bigImage);
                    cb_seller_name.setText(""+bean.sellerName);
                    tv_seller_tel.setText(""+bean.mobile);
                    ll_seller.addView(sellerItem);
                    if(i==0){//---默认选中第一个
                        ll_seller.getChildAt(i).findViewById(R.id.ll_seller_info).setVisibility(View.VISIBLE);
                        itemParams.setSellerId(""+bean.sellerId);
                        sellerInfo = bean;
                        getServiceItem(itemParams);
                    }
                    sellerItem.setOnClickListener(new OnSellerClick(i));
                }
            }
        });
    }

    //---获取服务项信息
    public void getServiceItem(QueryServiceItemParams params){
        String url = MyHttpUrl.QUERY_SERVICE_ITEM +gson.toJson(params);

        MyLog.e(TAG , "获取服务项===="+url );

        MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QuerySellerItemJson>() {
            @Override
            public void onError(Request request, Exception e) {
                MyLog.e(TAG , "网络请求失败=="+e.getMessage());
            }
            @Override
            public void onResponse(QuerySellerItemJson querySellerItemJson) {
                if(querySellerItemJson.RESULT_CODE==0){
                    List<QuerySellerDetailJson.ServiceItem> itemList = querySellerItemJson.RESULT_DATA.ssiLstMap;
                    rv_service_item.setAdapter(new ServiceItemAdapter(getActivity() , itemList));
                }
            }
        });

    }

    /**展开的Item的位置*/
    private int expandPosition = -1;

    public class OnSellerClick implements View.OnClickListener{
        private int position;
        public OnSellerClick(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            for(int i=0 ; i<ll_seller.getChildCount() ; i++){
                if (i == position){
                    if(i==expandPosition && sellerList!=null && sellerList.size()>0){//跳转界面
                        QueryServiceInfoJson.SellerInfo adverSeller = sellerList.get(expandPosition);
                        SellerDetailActivity.startAction(getActivity() , adverSeller.sellerId);
                    }
                    ll_seller.getChildAt(i).findViewById(R.id.ll_seller_info).setVisibility(View.VISIBLE);
                    itemParams.setSellerId(""+sellerList.get(position).sellerId);
                    sellerInfo = sellerList.get(position);
                    getServiceItem(itemParams);
                    expandPosition = position;
                }else{
                    ll_seller.getChildAt(i).findViewById(R.id.ll_seller_info).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == TO_CAPTURE) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            MyToast.showShort(mContext, "result=" + result);
            int sellerId = Integer.parseInt(result.trim());
            SellerDetailActivity.startAction(getActivity() , sellerId);
        }
    }
}
