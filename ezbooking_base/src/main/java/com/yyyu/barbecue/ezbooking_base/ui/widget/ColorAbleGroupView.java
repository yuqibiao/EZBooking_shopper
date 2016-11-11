package com.yyyu.barbecue.ezbooking_base.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yyyu.barbecue.ezbooking_base.annotate.ContentView;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：一组ColorAbleCroup 实现类似RadioGroup的功能
 * <p/>
 * Created by yyyu on 2016/10/27.
 */
public class ColorAbleGroupView extends LinearLayout {

    private static final String TAG = "ColorAbleGroupView";

    private int selectedPosition = -1;
    private OnItemClickListener mOnItemClickListener;

    public ColorAbleGroupView(Context context) {
        this(context , null);
    }

    public ColorAbleGroupView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ColorAbleGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            if(getChildAt(i) instanceof  ColorAbleImageView){
                ColorAbleImageView cView = (ColorAbleImageView) getChildAt(i);
                //--设置默认
                if (i == 0) {
                    cView.setColour(true);
                }
                final int position = i;
                cView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //---设置选中颜色
                        selectedPosition = position;
                        for (int j = 0; j < getChildCount(); j++) {
                            final ColorAbleImageView cView;
                            cView = (ColorAbleImageView) getChildAt(j);
                            if (j == selectedPosition) {
                                cView.setColour(true);
                            } else {
                                cView.setColour(false);
                            }
                        }
                        if(mOnItemClickListener != null){
                            mOnItemClickListener.onItemClick(selectedPosition);
                        }
                    }
                });
            }
        }
    }


    /**
     * 得到被选择的View位置
     *
     * @return
     */
    public int getSelectedPosition() {

        return selectedPosition;
    }

    /**
     * 设置Item点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * Item点击时间接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
