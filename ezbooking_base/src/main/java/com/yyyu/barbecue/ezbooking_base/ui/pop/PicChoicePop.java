package com.yyyu.barbecue.ezbooking_base.ui.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.yyyu.barbecue.ezbooking_base.R;
import com.yyyu.barbecue.ezbooking_base.utils.MediaUtils;

/**
 * 功能：图片选择pop
 *
 * Created by yyyu on 2016/7/27.
 */
public class PicChoicePop extends BasePopupWindow {

    public PicChoicePop(Activity act, int width, int height, View popView) {
        super(act, width, height, popView);
        setAnimationStyle(R.style.pic_choice);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        addOnClickListener(R.id.btn_to_camera,
                R.id.btn_to_photo, R.id.btn_to_cancel);
    }

    public void show(View popLocView) {
        showAtLocation(popLocView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_to_camera) {//拍照
            MediaUtils.toCamera(mAct);
            dismiss();
        } else if (v.getId() == R.id.btn_to_photo) {//相册
            MediaUtils.toGallery(mAct);
            dismiss();
        } else if (v.getId() == R.id.btn_to_cancel) {//取消
            dismiss();
        }
    }
}
