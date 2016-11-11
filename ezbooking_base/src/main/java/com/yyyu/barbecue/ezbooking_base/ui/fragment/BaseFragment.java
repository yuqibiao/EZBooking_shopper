package com.yyyu.barbecue.ezbooking_base.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInjectUtils;


/**
 * 功能：fragment的基类
 *
 * @author yyyu
 * @date 2016/5/18
 */
public abstract  class BaseFragment extends Fragment {

    private View rootView;
    protected Context mContext;
    protected Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.from(getActivity()).inflate(getLayoutId() , container , false);
        mContext = getContext();
        ViewInjectUtils.injectView(this , rootView);
        ViewInjectUtils.injectEvents(this ,rootView);
        gson = new Gson();
        beforeInit();
        initView();
        initListener();
        initData();
        afterInit();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 得到fragment布局文件id的抽像方法
     */
    public abstract  int getLayoutId();

    protected void beforeInit() {
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected void initData() {
    }

    protected void afterInit() {
    }


}
