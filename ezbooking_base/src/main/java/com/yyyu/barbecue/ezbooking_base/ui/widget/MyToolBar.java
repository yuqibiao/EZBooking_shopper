package com.yyyu.barbecue.ezbooking_base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.R;


/**
 * 功能：
 * Created by yyyu on 2016/6/28.
 */
public class MyToolBar extends LinearLayout{

    private TextView tv_tb_title;

    public MyToolBar(Context context) {
        this(context , null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View toolbar = LayoutInflater.from(context).inflate(R.layout.pt_toolbar ,this , true);
        tv_tb_title = (TextView) toolbar.findViewById(R.id.tv_tb_title);
    }

    public TextView getTitle() {
        return tv_tb_title;
    }

}
