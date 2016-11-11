package com.yyyu.ezbooking_shopper.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;

import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseActivity;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ColorAbleGroupView;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ColorAbleImageView;
import com.yyyu.barbecue.ezbooking_base.ui.widget.WrapContentHeightViewPager;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.ServiceItemAdapter;

/**
 * 功能：
 * Created by yyyu on 2016/9/19.
 */
public class TestActivity extends AppCompatActivity{

    private static final String TAG = "TestActivity";

    private ColorAbleImageView civ_test;
    private ColorAbleGroupView cvg_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
