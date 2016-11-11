package com.yyyu.ezbooking_shopper.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yyyu.barbecue.ezbooking_base.annotate.OnClick;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseActivity;
import com.yyyu.barbecue.ezbooking_base.ui.widget.loading.ShapeLoadingDialog;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.GoodsTypeAdapter;
import com.yyyu.ezbooking_shopper.adapter.ServiceItemAdapter;
import com.yyyu.ezbooking_shopper.bean.foo.MapMarkInfoBean;
import com.yyyu.ezbooking_shopper.bean.json.QuerySellerDetailJson;
import com.yyyu.ezbooking_shopper.bean.param.ToConfirmOrderParams;
import com.yyyu.ezbooking_shopper.bean.param.QuerySellerDetailParams;
import com.yyyu.ezbooking_shopper.callback.OnPickerFinishedListener;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.ui.dialog.DateTimePickerDialog;
import com.yyyu.ezbooking_shopper.utils.LogicUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：商家详情界面
 *
 * @author yyyu
 * @date 2016/7/17
 */
public class SellerDetailActivity extends BaseActivity {

    private static final String TAG = "SellerDetailActivity";

    @ViewInject(value = R.id.tv_tb_title)
    private TextView tv_tb_title;
    @ViewInject(value = R.id.tv_seller_tel)
    private TextView tv_seller_tel;
    @ViewInject(value = R.id.tv_seller_name)
    private TextView tv_seller_name;
    @ViewInject(value = R.id.iv_seller_icon)
    private ImageView iv_seller_icon;
    @ViewInject(value = R.id.tv_seller_intro)
    private TextView tv_seller_intro;
    @ViewInject(value = R.id.tv_picker_info)
    private TextView tv_picker_info;
    @ViewInject(value = R.id.lv_goods_show)
    private ListView lv_goods_show;
    @ViewInject(value = R.id.cb_order_dinner)
    private CheckBox cb_order_dinner;
    @ViewInject(R.id.rv_service_item)
    private RecyclerView rv_service_item;

    private int sellerId;
    private Gson gson;
    private String url;
    private DateTimePickerDialog pickerDialog;
    private ToConfirmOrderParams toCreateOrderParams;
    private GoodsTypeAdapter goodsTypeAdapter;
    private boolean isPicked = false;
    private String sellerName;
    private String sellerTel;
    private String sellerIcon;
    private ShapeLoadingDialog loadingDialog;
    private String lng;
    private String lat;
    private String sellerIntro;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        gson = new Gson();
        Intent intent = getIntent();
        sellerId = intent.getIntExtra("sellerId", -1);
        QuerySellerDetailParams param = new QuerySellerDetailParams(sellerId);
        String paramStr = gson.toJson(param);
        url = MyHttpUrl.QUERY_SELLER_DETAIL + paramStr;
        pickerDialog = new DateTimePickerDialog(this);
        toCreateOrderParams = new ToConfirmOrderParams();
        loadingDialog = new ShapeLoadingDialog(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seller_detail;
    }

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    protected void initView() {
        loadingDialog.show();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_service_item.setLayoutManager(layoutManager);
    }

    @Override
    protected void initListener() {
        pickerDialog.setOnPickerFinishedListener(new OnPickerFinishedListener() {
            @Override
            public void onPickerFinished() {
                tv_picker_info.setText(pickerDialog.getInfo());
                pickerDialog.dismiss();
                isPicked = true;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        MyLog.e(TAG, "商店详情url==" + url);
        MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url),
                new MyHttpManager.ResultCallback<QuerySellerDetailJson>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        MySnackBar.showShortDef(SellerDetailActivity.this, "网络加载失败！！！");
                    }

                    @Override
                    public void onResponse(QuerySellerDetailJson detailJson) {
                        if (detailJson.RESULT_CODE == 0) {
                            sellerName = detailJson.RESULT_DATA.sellerName;
                            sellerIcon = detailJson.RESULT_DATA.bigImage;
                            sellerTel = detailJson.RESULT_DATA.mobile;
                            sellerIntro = detailJson.RESULT_DATA.sellerDesc;
                            lng = detailJson.RESULT_DATA.lng;
                            lat = detailJson.RESULT_DATA.lat;
                            tv_tb_title.setText(sellerName);
                            tv_seller_name.setText(sellerName);
                            MyBitmapUtils.getInstance(SellerDetailActivity.this).display(iv_seller_icon,
                                    detailJson.RESULT_DATA.bigImage);
                            if (TextUtils.isEmpty(detailJson.RESULT_DATA.sellerDesc)) {
                                tv_seller_intro.setText("目前该商家还没有简介");
                            } else {
                                tv_seller_intro.setText(detailJson.RESULT_DATA.sellerDesc);
                            }
                            tv_seller_tel.setText("商家电话  " + sellerTel);
                            goodsTypeAdapter = new GoodsTypeAdapter
                                    (SellerDetailActivity.this, detailJson.RESULT_DATA.goodsTypeList, sellerName, sellerIcon);
                            lv_goods_show.setAdapter(goodsTypeAdapter);
                            List<QuerySellerDetailJson.ServiceItem> serviceItemList = detailJson.RESULT_DATA.serviceItemList;
                            rv_service_item.setAdapter(new ServiceItemAdapter(SellerDetailActivity.this , serviceItemList));

                        } else {
                            MySnackBar.showShortDef(SellerDetailActivity.this, "获取数据失败");
                        }

                        loadingDialog.dismiss();
                    }
                });
    }

    @OnClick(value = {R.id.ib_tb_left, R.id.ib_tb_right
            , R.id.tv_seller_tel, R.id.tv_submit, R.id.tv_picker_info})
    public void onInjectClick(View view) {
        switch (view.getId()) {
            case R.id.ib_tb_left://---返回
                this.finish();
                break;
            case R.id.ib_tb_right://---在地图上显示商家的位置
                if(TextUtils.isEmpty(lng)  || TextUtils.isEmpty(lat)){
                    MySnackBar.showShortDef(this , "该商家目前没有地址信息");
                    break;
                }
                MapMarkInfoBean markBean = new MapMarkInfoBean(
                        Double.parseDouble(lng), Double.parseDouble(lat), sellerName, sellerIntro);
                MapShowActivity.startAction(this, markBean);
                break;
            case R.id.tv_seller_tel: {//---商家电话
                String telStr = tv_seller_tel.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telStr.substring(4, telStr.length())));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case R.id.tv_submit://--确认订单
                if (!isPicked) {
                    MySnackBar.showLongDef(this, "请选择订单信息！！！").setAction("选择", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickerDialog.show();
                        }
                    });
                    break;
                }
                int orderType;
                if (cb_order_dinner.isChecked()) {
                    orderType = 0;
                } else {
                    orderType = 1;
                }
                toCreateOrderParams.setUserId(LogicUtils.getInstance(getApplicationContext()).getUserId());
                toCreateOrderParams.setSellerId(sellerId);
                toCreateOrderParams.setOrderDate(pickerDialog.getDate());
                toCreateOrderParams.setOrderTime(pickerDialog.getTime());
                toCreateOrderParams.setPersonCount(Integer.parseInt(pickerDialog.getPersonNum()));
                toCreateOrderParams.setDeskCount(Integer.parseInt(pickerDialog.getTableNum()));
                if (orderType == 0) {//---有预约商品的时候才有这些参数
                    toCreateOrderParams.setPayType(2); //---支付宝
                    List<ToConfirmOrderParams.SubOrder> subOrders = new ArrayList<>();
                    for (Map.Entry<Integer, ToConfirmOrderParams.SubOrder> entry : goodsTypeAdapter.subOrderContainer.entrySet()) {
                        subOrders.add(entry.getValue());
                    }
                    toCreateOrderParams.setSubOrders(subOrders);
                }
                OrderConfirmActivity.startAction(this, gson.toJson(toCreateOrderParams), sellerName, sellerTel, orderType);
                break;
            case R.id.tv_picker_info://选择订单信息
                pickerDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == OrderConfirmActivity.ORDER_CONFIRM) {
                if (data.getBooleanExtra("confirm", false)) {
                    SellerDetailActivity.this.finish();
                }
            }
        }
    }

    /**
     * 启动该Activity
     *
     * @param activity
     * @param sellerId
     */
    public static void startAction(Activity activity, int sellerId) {
        Intent intent = new Intent(activity, SellerDetailActivity.class);
        intent.putExtra("sellerId", sellerId);
        activity.startActivity(intent);
    }

}
