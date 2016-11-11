package com.yyyu.ezbooking_shopper.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yyyu.barbecue.ezbooking_base.annotate.ViewInject;
import com.yyyu.barbecue.ezbooking_base.gobal.Constant;
import com.yyyu.barbecue.ezbooking_base.net.MyBitmapUtils;
import com.yyyu.barbecue.ezbooking_base.net.MyHttpManager;
import com.yyyu.barbecue.ezbooking_base.ui.fragment.BaseFragment;
import com.yyyu.barbecue.ezbooking_base.ui.widget.StereoView;
import com.yyyu.barbecue.ezbooking_base.utils.ActivityHolder;
import com.yyyu.barbecue.ezbooking_base.utils.MediaUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyFileOprateUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.barbecue.ezbooking_base.utils.MyNetUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySPUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MySnackBar;
import com.yyyu.barbecue.ezbooking_base.utils.MyToast;
import com.yyyu.ezbooking_shopper.R;
import com.yyyu.ezbooking_shopper.adapter.HistorySellerAdapter;
import com.yyyu.ezbooking_shopper.bean.foo.LogoutEvent;
import com.yyyu.ezbooking_shopper.bean.json.QueryMyInfoJson;
import com.yyyu.ezbooking_shopper.bean.json.ResultJson;
import com.yyyu.ezbooking_shopper.bean.param.ModifyUserInfoParams;
import com.yyyu.ezbooking_shopper.bean.param.QueryMyInfoParams;
import com.yyyu.ezbooking_shopper.bean.param.UploadImgParams;
import com.yyyu.ezbooking_shopper.callback.OnPickerFinishedListener;
import com.yyyu.ezbooking_shopper.net.MyHttpUrl;
import com.yyyu.ezbooking_shopper.ui.activity.SellerDetailActivity;
import com.yyyu.ezbooking_shopper.ui.activity.SplashActivity;
import com.yyyu.ezbooking_shopper.ui.dialog.ChoiceAddress;
import com.yyyu.ezbooking_shopper.ui.dialog.ChoicePicDialog;
import com.yyyu.ezbooking_shopper.utils.LogicUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 功能：我的界面
 *
 * @author yyyu
 * @date 2016/6/26
 */
public class MyFragment extends BaseFragment {

    private static final String TAG = "MyFragment";

    @ViewInject(value = R.id.civ_user_icon)
    private CircleImageView civ_user_icon;
    @ViewInject(value = R.id.tv_username)
    private TextView tv_username;
    @ViewInject(value = R.id.tv_user_tel)
    private TextView tv_user_tel;
    @ViewInject(value = R.id.tv_username2)
    private TextView tv_username2;
    @ViewInject(value = R.id.tv_user_tel2)
    private TextView tv_user_tel2;
    @ViewInject(value = R.id.tv_user_address)
    private TextView tv_user_address;
    @ViewInject(value = R.id.gv_history_seller)
    GridView gv_history_seller;
    @ViewInject(value = R.id.iv_edit_user_info)
    private ImageView iv_edit_user_info;
    @ViewInject(value = R.id.sv_user_info)
    private StereoView sv_user_info;
    @ViewInject(value = R.id.tv_edit_finished)
    private TextView tv_edit_finished;
    @ViewInject(R.id.et_username)
    private EditText et_username;
    @ViewInject(value = R.id.et_user_tel)
    private EditText et_user_tel;
    @ViewInject(value = R.id.et_user_address)
    private EditText et_user_address;
    @ViewInject(value = R.id.tv_choice_address)
    private TextView tv_choice_address;
    @ViewInject(R.id.tv_logout)
    private TextView tv_logout;

    private HistorySellerAdapter historySellerAdapter;
    private QueryMyInfoParams queryMyInfoParams;
    private ModifyUserInfoParams modifyUserInfoParams;
    private ChoicePicDialog dialog;
    private UploadImgParams uploadImgParams;
    private List<QueryMyInfoJson.Seller> historySellerList;

    @Override
    protected void beforeInit() {
        super.beforeInit();
        queryMyInfoParams = new QueryMyInfoParams();
        modifyUserInfoParams = new ModifyUserInfoParams();
        uploadImgParams = new UploadImgParams();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        if (LogicUtils.getInstance(mContext).isUserLogined()) {
            tv_logout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        //---点击历史商家
        gv_history_seller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (historySellerList != null) {
                    QueryMyInfoJson.Seller historySeller = historySellerList.get(position);
                    SellerDetailActivity.startAction(getActivity(), historySeller.sellerId);
                }
            }
        });
        //---编辑用户信息
        iv_edit_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_username.setText(tv_username.getText().toString());
                et_user_tel.setText(tv_user_tel.getText());
                et_user_address.setText(tv_user_address.getText().toString());
                sv_user_info.toNext();
            }
        });
        //---提交修改
        tv_edit_finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---修改用户信息
                modifyUserInfoParams.setUserName(et_username.getText().toString());
                modifyUserInfoParams.setMobile(et_user_tel.getText().toString());
                modifyUserInfoParams.setUserAddress(et_user_address.getText().toString());
                String url = MyHttpUrl.MODIFY_MY_INFO + gson.toJson(modifyUserInfoParams);
                modifyUserInfo(url);
                sv_user_info.toPre();
            }
        });
        //---选择图片
        civ_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ChoicePicDialog(mContext, MyFragment.this);
                dialog.show();
            }
        });
        //---选择地址
        tv_choice_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChoiceAddress choiceAddress = new ChoiceAddress(mContext);
                choiceAddress.show();
                choiceAddress.setOnPickerFinishedListener(new OnPickerFinishedListener() {
                    @Override
                    public void onPickerFinished() {
                        et_user_address.setText(choiceAddress.getAddress());
                    }
                });
            }
        });
        //---注销用户
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("注销用户")
                        .setMessage("确定要残忍的退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MySPUtils.remove(mContext, Constant.USER_INFO);
                                startActivity(new Intent(getActivity() , SplashActivity.class));
                                ActivityHolder.finishedAll();
                               // EventBus.getDefault().post(new LogoutEvent("注销了用户"));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

    }

    private void modifyUserInfo(String url) {
        MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<ResultJson>() {
            @Override
            public void onError(Request request, Exception e) {
                MyLog.e(TAG, "网络连接出错==" + e.getMessage());
                MySnackBar.showShortDef(getActivity(), "请检查你的网络");
            }

            @Override
            public void onResponse(ResultJson resultJson) {
                if (resultJson.RESULT_CODE == 0) {
                    MySnackBar.showShortDef(getActivity(), resultJson.RESULT_DATA.SUCCESS_MESSAGE);
                    initData();
                } else {
                    MySnackBar.showShortDef(getActivity(), "内部错误==" + resultJson.RESULT_DATA.SUCCESS_MESSAGE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (LogicUtils.getInstance(mContext).isUserLogined()) {
            int userId = LogicUtils.getInstance(mContext).getUserId();
            queryMyInfoParams.setUserId(userId);
            modifyUserInfoParams.setUserId(userId);
            uploadImgParams.setUserId(userId);
            String url = MyHttpUrl.QUERY_MY_INFO + gson.toJson(queryMyInfoParams);
            getUserInfoFromNet(url);
        }
    }

    /**
     * 获取用户信息
     *
     * @param url
     */
    private void getUserInfoFromNet(String url) {
        MyHttpManager.getAsyn(MyNetUtils.escapeUrl(url), new MyHttpManager.ResultCallback<QueryMyInfoJson>() {
            @Override
            public void onError(Request request, Exception e) {
                MyLog.e(TAG, "网络加载失败==" + e.getMessage());
            }

            @Override
            public void onResponse(QueryMyInfoJson queryMyInfoJson) {
                QueryMyInfoJson.MyInfo myInfo = queryMyInfoJson.RESULT_DATA;
                tv_username.setText(myInfo.userName);
                tv_username2.setText(myInfo.userName);
                tv_user_tel.setText(myInfo.mobile);
                tv_user_tel2.setText(myInfo.mobile);
                tv_user_address.setText(myInfo.userAddress);
                MyBitmapUtils.getInstance(mContext).display(civ_user_icon, MyHttpUrl.FILE_BASE + myInfo.userImages);
                historySellerList = myInfo.sellerList;
                if (historySellerList == null) {
                    return;
                }
                //---init gridView
                int length = 100;
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
                int gridviewWidth = (int) (historySellerList.size() * (length + 4) * density);
                int itemWidth = (int) (length * density);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                gv_history_seller.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                gv_history_seller.setColumnWidth(itemWidth); // 设置列表项宽
                gv_history_seller.setHorizontalSpacing(5); // 设置列表项水平间距
                gv_history_seller.setStretchMode(GridView.NO_STRETCH);
                gv_history_seller.setNumColumns(historySellerList.size()); // 设置列数量=列表集合数
                historySellerAdapter = new HistorySellerAdapter(mContext, historySellerList);
                gv_history_seller.setAdapter(historySellerAdapter);
            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MediaUtils.PHOTO_REQUEST_CAMERA: {//相机
                if (getActivity().RESULT_OK != resultCode) break;
                File iconFile = new File(MediaUtils.filePath, MediaUtils.PHOTO_FILE_NAME);
                MediaUtils.crop(this, Uri.fromFile(iconFile));
                break;
            }
            case MediaUtils.PHOTO_REQUEST_GALLERY: {//相册
                if (resultCode == getActivity().RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    MediaUtils.crop(this, uri);
                }
                break;
            }
            case MediaUtils.PHOTO_REQUEST_CUT: {//剪切
                if (getActivity().RESULT_OK != resultCode) break;
                Bitmap bitmap = data.getParcelableExtra("data");
                String savePath = MyFileOprateUtils.saveBitmap(getActivity(), bitmap, "crop_temp.jpg");
                uploadImgParams.setUserImages(MyFileOprateUtils.imgToBase64(savePath, mContext));
                Map<String, String> params = new HashMap<>();
                params.put("method", "user.modifyUserImage");
                params.put("data", gson.toJson(uploadImgParams));
                //---上传图片
                MyHttpManager.postAsyn(MyHttpUrl.MODIFY_USER_ICON, new MyHttpManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        MyLog.e(TAG, "上传头像出错===" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response) {
                        MyLog.e(TAG, "返回==" + response);
                    }
                }, params);
                civ_user_icon.setImageBitmap(bitmap);
                break;
            }
        }
    }
}
