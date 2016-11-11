package com.yyyu.ezbooking_shopper.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Request;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseActivity;
import com.yyyu.barbecue.ezbooking_base.ui.widget.NoScrollListView;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.ConfirmGoodsAdapter;
import com.yyyu.ezbooking_shopper.bean.json.ResultJson;
import com.yyyu.ezbooking_shopper.bean.param.ToConfirmOrderParams;
import com.yyyu.ezbooking_shopper.callback.OnPickerFinishedListener;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.ui.dialog.DateTimePickerDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * 功能：订单确认Activity
 *
 * @author yyyu
 * @date 2016/7/30
 */
public class OrderConfirmActivity extends BaseActivity {

    private static final String TAG = "OrderConfirmActivity";

    @ViewInject(value = R.id.lv_confirm_goods)
    private NoScrollListView lv_confirm_goods;
    @ViewInject(value = R.id.tv_tb_title)
    private TextView tv_tb_title;
    @ViewInject(value = R.id.ib_tb_right)
    private ImageButton ib_tb_right;
    @ViewInject(value = R.id.tv_seller_name)
    private TextView tv_seller_name;
    @ViewInject(R.id.tv_seller_tel)
    private TextView tv_seller_tel;
    @ViewInject(R.id.tv_order_datetime)
    private TextView tv_order_datetime;
    @ViewInject(value = R.id.tv_table_num)
    private TextView tv_table_num;
    @ViewInject(value = R.id.tv_person_num)
    private TextView tv_person_num;
    @ViewInject(value = R.id.tv_order_seller_name)
    private TextView tv_order_seller_name;
    @ViewInject(R.id.et_words_left)
    private EditText et_words_left;
    @ViewInject(R.id.tv_total_price)
    private TextView tv_total_price;
    @ViewInject(R.id.tv_pay_datetime)
    private TextView tv_pay_datetime;
    @ViewInject(R.id.tv_pay_type)
    private TextView tv_pay_type;
    @ViewInject(value = R.id.ll_pay_info)
    private LinearLayout ll_pay_info;

    public static final int ORDER_CONFIRM=1;
    private DateTimePickerDialog dateTimePickerDialog;
    private String sellerName;
    private String sellerTel;
    private ToConfirmOrderParams toConfirmOrderParams;
    private double allTotalPrice;
    private int orderType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        Intent intent = getIntent();
        sellerName = intent.getStringExtra("sellerName");
        sellerTel = intent.getStringExtra("sellerTel");
        orderType = intent.getIntExtra("orderType" , -1);
        String subOrdersStr = intent.getStringExtra("subOrders");
        toConfirmOrderParams = gson.fromJson(subOrdersStr, ToConfirmOrderParams.class);
        dateTimePickerDialog = new DateTimePickerDialog(this);
        if(orderType == 0){
            for (ToConfirmOrderParams.SubOrder subOrder :toConfirmOrderParams.getSubOrders()){
                allTotalPrice +=Double.parseDouble(subOrder.getGoodsTotalPrice());
            }
        }
        //---转json的时候忽略的字段
        ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().contains("goodsName")||
                        f.getName().contains("goodsTotalPrice")||
                        f.getName().contains("bigImage");
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        gson = new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();

    }

    @Override
    protected void initView() {
        tv_tb_title.setText("预定订单");
        ib_tb_right.setVisibility(View.INVISIBLE);
        tv_seller_name.setText(sellerName);
        tv_seller_tel.setText(sellerTel);
        tv_order_seller_name.setText(sellerName);
        tv_pay_datetime.setText(toConfirmOrderParams.getOrderDate());
        tv_order_datetime.setText(toConfirmOrderParams.getOrderDate()
                +"  "+toConfirmOrderParams.getOrderTime());
        tv_table_num.setText(toConfirmOrderParams.getDeskCount()+"桌");
        tv_person_num.setText(toConfirmOrderParams.getPersonCount()+"人");
        if(orderType ==0){
            ll_pay_info.setVisibility(View.VISIBLE);
            tv_total_price.setText(""+allTotalPrice+"RMB");
            if(toConfirmOrderParams.getPayType()==1){
                tv_pay_type.setText("微信支付");
            }else if(toConfirmOrderParams.getPayType()==2){
                tv_pay_type.setText("支付宝支付");
            }
        }
    }

    @Override
    protected void initListener() {
        addOnClickListener(R.id.ib_tb_left, R.id.iv_edit_form,
                R.id.tv_confirm);
        //---选择订单日期等信息完成
        dateTimePickerDialog.setOnPickerFinishedListener(new OnPickerFinishedListener() {
            @Override
            public void onPickerFinished() {
                String datetimeInfo = "" + dateTimePickerDialog.getDate()
                        + "    " + dateTimePickerDialog.getTime();
                tv_order_datetime.setText(datetimeInfo);
                tv_table_num.setText(dateTimePickerDialog.getTableNum() + "桌");
                tv_person_num.setText(dateTimePickerDialog.getPersonNum() + "人");
                toConfirmOrderParams.setOrderDate(dateTimePickerDialog.getDate());
                toConfirmOrderParams.setOrderTime(dateTimePickerDialog.getTime());
                toConfirmOrderParams.setDeskCount(Integer.parseInt(dateTimePickerDialog.getTableNum()));
                toConfirmOrderParams.setPersonCount(Integer.parseInt(dateTimePickerDialog.getPersonNum()));
                dateTimePickerDialog.dismiss();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (orderType == 0){
            lv_confirm_goods.setAdapter(new ConfirmGoodsAdapter(this, toConfirmOrderParams.getSubOrders()));
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ib_tb_left:
                this.finish();
                break;
            case R.id.iv_edit_form://---修改订单信息
                dateTimePickerDialog.show();
                break;
            case R.id.tv_confirm://---确认预定
                toConfirmOrderParams.setOrderDesc(et_words_left.getText().toString());
                String url = MyHttpUrl.TO_CREATE_ORDER+gson.toJson(toConfirmOrderParams);
                MyLog.e(TAG , "url------"+url);
                MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<ResultJson>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        MySnackBar.showShortDef(OrderConfirmActivity.this , "下单失败！！！检查网络！！！");
                        MyLog.e(TAG , "网络请求出错==="+e.getMessage());
                    }
                    @Override
                    public void onResponse(ResultJson resultJson) {
                        if(resultJson.RESULT_CODE==0){
                            MySnackBar.showShortDef(OrderConfirmActivity.this , resultJson.RESULT_DATA.SUCCESS_MESSAGE);
                            EventBus.getDefault().post("order_confirm");
                            Intent intent = new Intent();
                            intent.putExtra("confirm" , true);
                            OrderConfirmActivity.this.setResult(RESULT_OK , intent);
                            OrderConfirmActivity.this.finish();
                        }else{
                            MySnackBar.showShortDef(OrderConfirmActivity.this , "内部错误！！下单失败！！");
                        }
                    }
                });
                break;
        }
    }

    /**
     * 启动该Activity
     * @param activity
     * @param subOrders 商店商品对应的Json字符串
     * @param sellerName 商店名
     * @param sellerTel 商店电话
     * @param orderType 订单类型 0：预约+预定  1:仅预约
     */
    public static void startAction(Activity activity, String subOrders, String sellerName, String sellerTel , int orderType) {
        Intent intent = new Intent(activity, OrderConfirmActivity.class);
        intent.putExtra("subOrders", subOrders);
        intent.putExtra("sellerName", sellerName);
        intent.putExtra("sellerTel", sellerTel);
        intent.putExtra("orderType" , orderType);
        activity.startActivityForResult(intent , ORDER_CONFIRM);
    }

}
