package com.yyyu.barbecue.ezbooking_base.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.yyyu.barbecue.ezbooking_base.R;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;

/**
 * 功能：自定义可作色的ImageView(针对图标)
 *
 * Created by yyyu on 2016/10/27.
 */
public class ColorAbleImageView extends ImageView{

    /*被选中时的颜色*/
    private int selectColor;
    /*原始的图片*/
    private Bitmap iconBitmap;
    /*画图区域大小*/
    private Rect drawRect;
    /*按控件大小缩放后的图片*/
    private Bitmap sBitmap;
    /*是否着色*/
    private boolean isColour;

    public ColorAbleImageView(Context context) {
        this(context , null);
    }

    public ColorAbleImageView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ColorAbleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ColorAbleImageView );
        selectColor = ta.getColor(R.styleable.ColorAbleImageView_select_color , 0xF5A623);
        ta.recycle();
        //---得到bitmap
        Drawable drawable = getDrawable();
        if(!(drawable instanceof BitmapDrawable)){
            throw new UnsupportedOperationException("src设置的必须为bitmap");
        }else{
            iconBitmap = ((BitmapDrawable) drawable).getBitmap();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到bitmap的宽高
        int bWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int bHeight = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        float wScale =(float) bWidth/iconBitmap.getWidth();
        float hScale = (float) bHeight/iconBitmap.getHeight();
        float scale = wScale<hScale ? wScale :hScale;
        Matrix matrix = new Matrix();
        matrix.postScale(scale ,scale);
        sBitmap = Bitmap.createBitmap(iconBitmap ,0 ,0 , iconBitmap.getWidth() ,iconBitmap.getHeight() , matrix ,true);
        int left =  getMeasuredWidth() / 2 - sBitmap.getWidth() / 2;
        int top = getMeasuredHeight()/2 - sBitmap.getHeight()/2;
        //绘制的区域大小
        drawRect = new Rect(left , top , left+sBitmap.getWidth() , top+sBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
       if(!isColour){
           canvas.drawBitmap(sBitmap , null , drawRect ,new Paint());
       }else{
           canvas.drawBitmap(colourBitmap(sBitmap , selectColor) , 0 , 0 , null);
       }
    }

    public void setColour(boolean isColour){
        this.isColour = isColour;
        postInvalidate();
    }


    /**
     * 给Bitmap着色
     *
     * @param icon
     * @param color
     */
    private Bitmap colourBitmap(Bitmap icon , int color){
        Paint mPaint = new Paint();
        Bitmap cBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cBitmap);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(color);
        canvas.drawRect(drawRect , mPaint);
        //---关键
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(icon , null ,drawRect , mPaint);
        return cBitmap;
    }

}
