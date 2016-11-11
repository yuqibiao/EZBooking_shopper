package com.yyyu.barbecue.ezbooking_base.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.yyyu.barbecue.ezbooking_base.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：PopupWindow的基类
 * Created by yyyu on 2016/7/27.
 */
public abstract class BasePopupWindow extends PopupWindow implements View.OnClickListener {

    protected Activity mAct;
    protected View popView;

    /**
     * View的容器
     */
    private Map<Integer, View> viewContainer = new HashMap<>();

    public BasePopupWindow(Activity act, int width, int height, View popView) {
        super(popView, width, height);
        this.popView = popView;
        this.mAct = act;
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        //setAnimationStyle(R.style.mypopwindow_anim_style);
        initView();
        initListener();
    }


    /**
     * 布局
     *
     * @return
     */
    public View getLayoutId(int layoutId) {
        return View.inflate(mAct ,  layoutId ,null);
    }

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 设置监听
     */
    protected abstract void initListener();

    /**
     * 得到Popup界面上的控件
     *
     * @param viewId
     * @return
     */
    protected <T extends View> T getView(int viewId) {
        if (viewContainer.containsKey(viewId)) {
            return (T) viewContainer.get(viewId);
        }
        return (T) popView.findViewById(viewId);
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
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        //设置主窗体背景变暗
        //backgroundAlpha(mAct , 0.4f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //当pop dismiss后主窗体背景恢复
        //backgroundAlpha(mAct , 1.0f);
    }

    public void backgroundAlpha(Activity act ,float bgAlpha)
    {
        WindowManager.LayoutParams lp = act.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        act.getWindow().setAttributes(lp);
    }
}
