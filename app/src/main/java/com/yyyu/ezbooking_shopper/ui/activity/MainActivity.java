package com.yyyu.ezbooking_shopper.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yyyu.barbecue.ezbooking_base.annotate.OnClick;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseActivity;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ChangeColorView;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.foo.LogoutEvent;
import com.yyyu.ezbooking_shopper.ui.fragment.MyFragment;
import com.yyyu.ezbooking_shopper.ui.fragment.OrderFragment;
import com.yyyu.ezbooking_shopper.ui.fragment.SellerFragment;
import com.yyyu.ezbooking_shopper.ui.fragment.ServiceFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {



    @ViewInject(value = R.id.vp_main )
    private ViewPager vp_main;

    @ViewInject(value = R.id.ccv_service)
    private ChangeColorView ccv_service;

    @ViewInject(value = R.id.ccv_seller)
    private ChangeColorView ccv_seller;

    @ViewInject(value = R.id.ccv_order)
    private ChangeColorView ccv_order;

    @ViewInject(value = R.id.ccv_my)
    private ChangeColorView ccv_my;

    private FragmentPagerAdapter tabsAdapter;
    private List<ChangeColorView> tabIndicators;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isSwipeBack() {
        return false;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        initTab();
        vp_main.setOffscreenPageLimit(3);
        vp_main.setAdapter(tabsAdapter);
    }

    private void initTab() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add(ccv_service);
        tabIndicators.add(ccv_seller);
        tabIndicators.add(ccv_order);
        tabIndicators.add(ccv_my);
        final List<Fragment> tabs = new ArrayList<>();
        tabs.add(new ServiceFragment());
        tabs.add(new SellerFragment());
        tabs.add(new OrderFragment());
        tabs.add(new MyFragment());
        FragmentManager fm = getSupportFragmentManager();
        tabsAdapter = new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return tabs.get(position);
            }

            @Override
            public int getCount() {
                return tabs.size();
            }
        };
        tabIndicators.get(0).setIconAlpha(1.0f);
        vp_main.setCurrentItem(0, false);
    }

    @Override
    protected void initListener() {
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset>0){
                    ChangeColorView left = tabIndicators.get(position);
                    ChangeColorView right = tabIndicators.get(position + 1);
                    left.setIconAlpha(1 - positionOffset);
                    right.setIconAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * EventBus 来自下单完成的Event
     * @param msg
     */
    @Subscribe(threadMode=ThreadMode.MAIN)
    public void checkedOrderFragment(String msg){
        if ("order_confirm".equals(msg)){
            resetOtherTabs();
            tabIndicators.get(2).setIconAlpha(1.0f);
            vp_main.setCurrentItem(2, false);
        }
    }

    /**
     * Eventbus 接收用户注销的事件
     * @param event
     */
    @Subscribe(threadMode=ThreadMode.MAIN)
    public void userLogoutEvent(LogoutEvent event){
       // initTab();
        //MySnackBar.showShortDef(this , event.msg);
    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            tabIndicators.get(i).setIconAlpha(0);
        }
    }

    @OnClick({R.id.ccv_service , R.id.ccv_seller , R.id.ccv_order , R.id.ccv_my})
    public void onInjectClick(View view) {
        switch (view.getId()) {
            case R.id.ccv_service:
                resetOtherTabs();
                tabIndicators.get(0).setIconAlpha(1.0f);
                vp_main.setCurrentItem(0, false);
                break;
            case R.id.ccv_seller:
                resetOtherTabs();
                tabIndicators.get(1).setIconAlpha(1.0f);
                vp_main.setCurrentItem(1, false);
                break;
            case R.id.ccv_order:
                resetOtherTabs();
                tabIndicators.get(2).setIconAlpha(1.0f);
                vp_main.setCurrentItem(2, false);
                break;
            case R.id.ccv_my:
                resetOtherTabs();
                tabIndicators.get(3).setIconAlpha(1.0f);
                vp_main.setCurrentItem(3, false);
                break;
        }
    }

    /**
     * 跳转到MainActivity
     *
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity , MainActivity.class);
        activity.startActivity(intent);
    }

}
