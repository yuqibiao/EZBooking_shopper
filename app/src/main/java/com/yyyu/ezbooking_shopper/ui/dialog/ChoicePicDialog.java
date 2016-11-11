package com.yyyu.ezbooking_shopper.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yyyu.barbecue.ezbooking_base.annotate.OnClick;
import com.yyyu.barbecue.ezbooking_base.ui.dialog.BaseDialog;
import com.yyyu.barbecue.ezbooking_base.utils.DimensChange;
import com.yyyu.barbecue.ezbooking_base.utils.MediaUtils;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;
import com.yyyu.ezbooking_shopper.R;

/**
 * 功能：选择图片Dialog
 *
 * @author yyyu
 * @date 2016/8/6
 */
public class ChoicePicDialog extends BaseDialog{

    private Fragment fragment ;

    public ChoicePicDialog(Context context , Fragment fragment){
        super(context);
        this.fragment = fragment;
        lp.width = DimensChange.dp2px(context , 280);
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_choice_pic;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        addOnClickListener(R.id.tv_to_photo , R.id.tv_to_camera);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_to_photo:{//---去相册选择
                MediaUtils.toGallery(fragment);
                dismiss();
                break;
            }
            case R.id.tv_to_camera:{//---去拍照
                MediaUtils.toCamera(fragment);
                dismiss();
                break;
            }
        }
    }
}
