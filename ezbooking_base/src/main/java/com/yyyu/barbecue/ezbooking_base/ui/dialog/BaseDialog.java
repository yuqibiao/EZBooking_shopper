package com.yyyu.barbecue.ezbooking_base.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.yyyu.barbecue.ezbooking_base.R;

import java.util.HashMap;
import java.util.Map;


/**
 * 自定义Dialog的base类
 *
 * @author yyyu
 * @date 2016-5-25
 */
public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    protected Context mContext;
    protected WindowManager.LayoutParams lp;
    private View view;

    /**
     * View的容器
     */
    private Map<Integer, View> viewContainer = new HashMap<>();

    public BaseDialog(Context context) {
        this(context, R.style.dialog);
    }

    private BaseDialog(Context context, int theme) {
        super(context, R.style.dialog);
        this.mContext = context;
        lp = getWindow().getAttributes();
        getWindow().setWindowAnimations(R.style.dialog_anim);
        this.setCanceledOnTouchOutside(true);//默认点击外面可消失
        view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        setContentView(view);
    }

    public void beforeInit() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        initView();
        initListener();
        initData();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    /**
     * Dialog 界面 id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 得到Dialog界面上的控件
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int viewId) {
        if (viewContainer.containsKey(viewId)) {
            return (T) viewContainer.get(viewId);
        }
        return (T) view.findViewById(viewId);
    }

    /**
     * 注册点击事件监听
     */
    protected void addOnClickListener(int... viewids) {
        for (int viewId : viewids) {
            getView(viewId).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewContainer.clear();
    }
}
