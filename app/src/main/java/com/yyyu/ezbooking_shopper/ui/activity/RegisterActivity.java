package com.yyyu.ezbooking_shopper.ui.activity;

import android.app.Activity;
import android.content.Intent;

import com.yyyu.barbecue.ezbooking_base.callback.OnDataSubmitListener;
import com.yyyu.barbecue.ezbooking_base.ui.activity.BaseRegAndLoginActivity;

/**
 * 功能：用户注册界面
 *
 * Created by yyyu on 2016/8/8.
 */
public class RegisterActivity extends BaseRegAndLoginActivity {

    @Override
    protected Integer setUserType() {
        return 1;
    }

    @Override
    protected Integer setOptionType() {
        return 0;
    }

    @Override
    protected void initListener() {
        super.initListener();
        setOnDataSubmitListener(new OnDataSubmitListener() {
            @Override
            public void onSuccess(String response) {
                LoginActivity.startAction(RegisterActivity.this);
                RegisterActivity.this.finish();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
    /**
     * 跳转到LoginActivity
     *
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity , RegisterActivity.class);
        activity.startActivity(intent);
    }
}
