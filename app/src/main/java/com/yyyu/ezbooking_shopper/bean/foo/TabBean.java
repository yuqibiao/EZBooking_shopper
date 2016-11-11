package com.yyyu.ezbooking_shopper.bean.foo;

import android.support.v4.app.Fragment;

/**
 * 功能： Tab Bean
 * Created by yyyu on 2016/7/6.
 */
public class TabBean {

    private int id;
    private String title;
    private Fragment fragment;

    public TabBean(){
    }

    public TabBean(int id, String title, Fragment fragment) {
        this.id = id;
        this.title = title;
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
