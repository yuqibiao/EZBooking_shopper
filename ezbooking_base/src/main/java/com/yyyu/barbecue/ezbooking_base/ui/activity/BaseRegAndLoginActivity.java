package com.yyyu.barbecue.ezbooking_base.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.yyyu.barbecue.ezbooking_base.R;
import com.yyyu.barbecue.ezbooking_base.bean.json.LoginJson;
import com.yyyu.barbecue.ezbooking_base.bean.params.LoginParams;
import com.yyyu.barbecue.ezbooking_base.callback.OnDataSubmitListener;
import com.yyyu.barbecue.ezbooking_base.gobal.Constant;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpUrl;
import com.yyyu.barbecue.ezbooking_base.ui.pop.PicChoicePop;
import com.yyyu.barbecue.ezbooking_base.ui.widget.loading.ShapeLoadingDialog;
import com.yyyu.barbecue.ezbooking_base.utils.FormValidationUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MediaUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyFileOprateUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MySPUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.barbecue.ezbooking_base.utils.MyToast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：针对商家端和和客户端注册、登录抽取的基类
 *
 * Created by yyyu on 2016/6/28.
 */
public abstract  class BaseRegAndLoginActivity extends BaseActivity{

    private static final String TAG = "BaseRegAndLoginActivity";

    private CircleImageView civ_user_icon;
    private EditText et_username;
    private EditText et_user_tel;
    private EditText et_password;
    private LoginParams loginParams;
    private View ll_login_root;
    private PicChoicePop picChoicePop;
    private ShapeLoadingDialog loadingDialog;
    private OnDataSubmitListener onDataSubmitListener;
    private TextInputLayout til_tel;
    private TextInputLayout til_password;
    private int optionType;
    private TextView tv_login_tip;
    private TextView tv_upload_icon;
    private LinearLayout ll_register;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean isSwipeBack() {
        return true;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        if(setOptionType() ==0){
            optionType = 0;
        }else if(setOptionType()==1){
            optionType = 1;
        }
        loginParams = new LoginParams();
        loginParams.setUserType(setUserType());
        View popView = View.inflate(this , R.layout.pop_pic_select , null);
        popView.measure(0 , 0);
        picChoicePop = new PicChoicePop(this ,
                WindowManager.LayoutParams.MATCH_PARENT,
                popView.getMeasuredHeight(),popView){
            @Override
            public void dismiss() {
                super.dismiss();
            }
            @Override
            public void show(View popLocView) {
                super.show(popLocView);
            }
        };
        loadingDialog = new ShapeLoadingDialog(this);
        loadingDialog.setLoadingText("注册中.....");
    }

    /**
     * 设置用户类型（商家还是客户）
     *
     * 1:用户
     *
     * @return
     */
    protected abstract Integer setUserType();

    /**
     * 设置是注册还是登录
     * 0：注册 1：登录
     * @return
     */
    protected abstract  Integer setOptionType();

    @Override
    protected void initView() {
        ll_login_root = getView(R.id.ll_login_root);
        tv_login_tip = getView(R.id.tv_login_tip);
        tv_upload_icon = getView(R.id.tv_upload_icon);
        ll_register = getView(R.id.ll_register);
        civ_user_icon = getView(R.id.civ_user_icon);
        et_username = getView(R.id.et_username);
        et_user_tel = getView(R.id.et_user_tel);
        et_password = getView(R.id.et_password);
        til_tel = getView(R.id.til_tel);
        til_password = getView(R.id.til_password);

        if(optionType == 0){//注册
            tv_login_tip.setText("请完成注册");
            tv_upload_icon.setVisibility(View.VISIBLE);
            ll_register.setVisibility(View.VISIBLE);
        }else if(optionType ==1){//登录
            tv_login_tip.setText("请完成登录");
        }
    }

    @Override
    protected void initListener() {
        if(optionType==0){
            addOnClickListener(R.id.civ_user_icon, R.id.tv_upload_icon,R.id.tv_find_pwd);
        }
        addOnClickListener(R.id.tv_next);
        checkEdit(til_tel , 0);
        checkEdit(til_password , 1);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    /**
     *TextInputLayout 输入提示（检测）
     *
     * @param textInputLayout
     * @param flag 0 ：手机号 1：密码
     */
    private void checkEdit(final TextInputLayout textInputLayout , final int flag){
        EditText editText =  textInputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                switch (flag){
                    case 0:{
                        if (!FormValidationUtils.isMobileNO(s.toString())){
                            textInputLayout.setError("请输入正确的手机号！！！");
                            textInputLayout.setErrorEnabled(true);
                        }else{
                            textInputLayout.setErrorEnabled(false);
                        }
                        break;
                    }
                    case 1:{
                        if (s.length()<6){
                            textInputLayout.setError("密码在6-15位之间！！！");
                            textInputLayout.setErrorEnabled(true);
                        }else {
                            textInputLayout.setErrorEnabled(false);
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MediaUtils.PHOTO_REQUEST_CAMERA:{//相机
                if (RESULT_OK != resultCode) break;
                File iconFile = new File(MediaUtils.filePath, MediaUtils.PHOTO_FILE_NAME);
                MediaUtils.crop(this, Uri.fromFile(iconFile));
                break;
            }
            case MediaUtils.PHOTO_REQUEST_GALLERY:{//相册
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    MediaUtils.crop(this, uri);
                }
                break;
            }
            case MediaUtils.PHOTO_REQUEST_CUT:{//剪切
                if (RESULT_OK != resultCode) break;
                Bitmap bitmap = data.getParcelableExtra("data");
                String savePath = MyFileOprateUtils.saveBitmap(this, bitmap , "crop_temp.jpg");
                loginParams.setUserImages(MyFileOprateUtils.imgToBase64(savePath , this));
                civ_user_icon.setImageBitmap(bitmap);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.civ_user_icon||view.getId()==R.id.tv_upload_icon){//上传头像
            picChoicePop.show(ll_login_root);
        }else if(view.getId()==R.id.tv_find_pwd){//找回密码
            MyToast.showShort(this , "该功能正在扩展");
        }else if(view.getId()==R.id.tv_next){//下一步
            if(optionType==0){
                String username = et_username.getText().toString();
                loginParams.setUserName(username);
                if(TextUtils.isEmpty(username)){
                    MySnackBar.showShortDef(BaseRegAndLoginActivity.this ,
                            "用户名不能为空！！！");
                    return;
                }
            }
            String userTel = et_user_tel.getText().toString();
            String password = et_password.getText().toString();
            loginParams.setPassword(password);
            loginParams.setMobile(userTel);
            if(til_password.isErrorEnabled() && til_tel.isErrorEnabled()){
                MySnackBar.showShortDef(BaseRegAndLoginActivity.this ,
                        "输入的信息有误！！！");
               return;
            }
            String url = MyHttpUrl.LOGIN_URL;
            submit(url);
        }
    }

    /**
     * 提交数据
     * @param url
     */
    private void submit(String url) {
        loadingDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("method" , "user.regLogin");
        params.put("data" , gson.toJson(loginParams));

        MyHttpManager.postAsyn(url, new MyHttpManager.ResultCallback<LoginJson>() {
            @Override
            public void onError(Request request, Exception e) {
                MyLog.e(TAG , "网络请求出错"+e.getMessage());
                MyToast.showShort(BaseRegAndLoginActivity.this , "网络加载失败");
                loadingDialog.dismiss();
                onDataSubmitListener.onFailure(e.getMessage());
            }
            @Override
            public void onResponse(LoginJson loginJson) {

                MyLog.e(TAG , gson.toJson(loginJson));

                if(loginJson.RESULT_CODE==0){
                    //---保存用户信息
                    MySPUtils.put(BaseRegAndLoginActivity.this , Constant.USER_INFO , gson.toJson(loginJson));
                    //---回调
                    onDataSubmitListener.onSuccess(gson.toJson(loginJson));
                }else{
                    MySnackBar.showShortDef(BaseRegAndLoginActivity.this , "内部错误！！！");
                }
                loadingDialog.dismiss();
            }
        } , params);
    }

    public void setOnDataSubmitListener(OnDataSubmitListener onDataSubmitListener){
        this.onDataSubmitListener = onDataSubmitListener;
    }

}
