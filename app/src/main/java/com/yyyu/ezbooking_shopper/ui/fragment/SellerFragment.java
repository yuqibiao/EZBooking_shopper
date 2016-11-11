package com.yyyu.ezbooking_shopper.ui.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yyyu.barbecue.ezbooking_base.annotate.OnClick;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.ui.fragment.BaseFragment;
import com.yyyu.barbecue.ezbooking_base.ui.widget.ColorAbleGroupView;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.bean.foo.TabBean;
import com.yyyu.ezbooking_shopper.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：商家界面
 *
 * @author yyyu
 * @date 2016/6/26
 */
public class SellerFragment extends BaseFragment {

    @ViewInject(value = R.id.id_sticky_scroll_indicator)
    private TabLayout tb_seller;
    @ViewInject(value = R.id.id_sticky_scroll_viewpager)
    private ViewPager vp_seller;
    @ViewInject(value = R.id.ccv_seller_type)
    private ColorAbleGroupView ccv_seller_type;

    private List<TabBean> tabs;
    private FragmentPagerAdapter adapter;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        initTab();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seller;
    }

    @Override
    protected void initView() {

    }

    /**
     * 初始化tab
     */
    private void initTab() {
        tabs = new ArrayList<>();
        tabs.add(new TabBean(1, "商家讯息", SellersShowFragment.newInstance(1)));
        tabs.add(new TabBean(2, "历史", new SellersShowFragment()));
        tabs.add(new TabBean(3, "全部", new SellersShowFragment()));
        adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabs.get(position).getFragment();
            }

            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position).getTitle();
            }

            @Override
            public long getItemId(int position) {
                return tabs.get(position).getId();
            }
        };
        vp_seller.setAdapter(adapter);
        tb_seller.setupWithViewPager(vp_seller);
    }

    @OnClick({R.id.lb_search , R.id.et_seller_search})
    public void toSearch(View view){
        SearchActivity.startAction(getActivity());
    }


    @Override
    protected void initListener() {
        ccv_seller_type.setOnItemClickListener(new ColorAbleGroupView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0://餐厅
                        ((SellersShowFragment) tabs.get(0).getFragment()).refreshDateBySellerType(1);
                        break;
                    case 1://小吃
                        ((SellersShowFragment) tabs.get(0).getFragment()).refreshDateBySellerType(2);
                        break;
                    case 2://休闲
                        ((SellersShowFragment) tabs.get(0).getFragment()).refreshDateBySellerType(3);
                        break;
                    case 3://服务
                        ((SellersShowFragment) tabs.get(0).getFragment()).refreshDateBySellerType(4);
                        break;
                }
            }
        });
    }


}
