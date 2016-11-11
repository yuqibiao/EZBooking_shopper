package com.yyyu.barbecue.ezbooking_base.ui.activity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yyyu.barbecue.ezbooking_base.R;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInjectUtils;
import com.yyyu.barbecue.ezbooking_base.ui.widget.swipeback.SwipeBackLayout;
import com.yyyu.barbecue.ezbooking_base.utils.ActivityHolder;
import com.yyyu.barbecue.ezbooking_base.utils.StatusBarCompat;

import java.util.HashMap;
import java.util.Map;


/**
 * 功能：Activity的基类
 *
 * @author yyyu
 * @date 2016/5/18
 */
public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener{

    private static final String TAG = "BaseActivity";

    /**
     *View的容器
     */
    private Map<Integer ,View> viewContainer = new HashMap<>();

    protected Gson gson;
    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 当前显示的Activity是========"+this.getClass().getName());
        ActivityHolder.addActivity(this);
        setContentView(getLayoutId());
        ViewInjectUtils.inject(this);//注入
        StatusBarCompat.compat(this , Color.parseColor("#ffffff"));
        initSwipe();
        beforeInit();
        initView();
        initListener();
        initData();
        afterInit();
    }

    private void initSwipe() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setSwipeBackEnable(isSwipeBack());
        mSwipeBackLayout.setEdgeSize(480);
    }

    /**
     * 得到layout的资源Id
     * @return
     */
    public abstract  int getLayoutId();

    /**
     * 是否滑动返回
     * @return true：允许
     */
    protected abstract boolean isSwipeBack();

    /**
     * 初始化之前
     */
    protected void beforeInit() {
        gson = new Gson();
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 注册监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化之后
     */
    protected void afterInit() {
    }

    /**
     * findViewById
     */
    protected <T extends View>T getView(int viewId){
        if(viewContainer.containsKey(viewId)){
            return (T) viewContainer.get(viewId);
        }
        return (T) findViewById(viewId);
    }

    /**
     * 注册点击事件监听
     */
    protected void addOnClickListener(int...viewids){
        for (int viewId:viewids) {
            getView(viewId).setOnClickListener(this);
        }
    }

    /**
     * 设置Empty界面
     */
    public void setEmptyView(){
        final FrameLayout rootView = (FrameLayout) findViewById(android.R.id.content);
        final View emptyView = LayoutInflater.from(this).inflate(R.layout.activity_empty , null);
        rootView.addView(emptyView);
        Button btn_retry = (Button) emptyView.findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView.removeView(emptyView);
                initData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewContainer.clear();
        ActivityHolder.removeActivity(this);
    }

    @Override
    public void onClick(View v) {

    }
}
