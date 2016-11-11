package com.yyyu.barbecue.ezbooking_base.anime;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.yyyu.barbecue.ezbooking_base.R;


/**
 * 功能：Item的动画
 *
 * @author yyyu
 * @date 2016/5/22
 */
public class ItemAnim {

    private  int lastPos = -1;

    private Context ctx;

    private static ItemAnim instance;

    private ItemAnim(Context ctx){
        this.ctx = ctx;
    }

    public static synchronized ItemAnim getInstance(Context ctx){
        while (instance ==null){
            instance = new ItemAnim(ctx);
        }
        return instance;
    }

    public void startAnimator(View view, int position) {
        if (position > lastPos) {
            view.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.item_bottom_in));
        }
        lastPos = position;
    }

}
