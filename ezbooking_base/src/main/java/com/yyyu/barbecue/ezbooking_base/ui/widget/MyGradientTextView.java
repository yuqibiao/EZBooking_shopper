package com.yyyu.barbecue.ezbooking_base.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.yyyu.barbecue.ezbooking_base.R;

/**
 * 渐变字体的TextView
 *
 * 参考地址：http://www.tuicool.com/articles/RF7v2qY
 *
 */
public class MyGradientTextView extends TextView {

	private LinearGradient mLinearGradient;
	private Matrix mGradientMatrix;
	private Paint mPaint;
	private int mViewWidth = 0;
	private int mTranslate = 0;
	private boolean mAnimating = true;
	private int startColor;
	private int centerColor;
	private int endColor;


	public MyGradientTextView(Context context) {
		this(context , null);
	}

	public MyGradientTextView(Context context, AttributeSet attrs) {
		this(context, attrs , 0);
	}

	public MyGradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//---获取自定义的属性
		TypedArray ta = context.obtainStyledAttributes(attrs , R.styleable.MyGradientTextView);
		startColor = ta.getColor(R.styleable.MyGradientTextView_start_color ,0x33ffffff);
		centerColor = ta.getColor(R.styleable.MyGradientTextView_center_color ,0xffffffff);
		endColor = ta.getColor(R.styleable.MyGradientTextView_end_color ,0x33ffffff);
		ta.recycle();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mViewWidth == 0) {
			mViewWidth = getMeasuredWidth();
			if (mViewWidth > 0) {
				mPaint = getPaint();
				mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
						new int[] { startColor, centerColor, endColor },
						new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
				mPaint.setShader(mLinearGradient);
				mGradientMatrix = new Matrix();
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mAnimating && mGradientMatrix != null) {
			mTranslate += mViewWidth / 10;
			if (mTranslate > 2 * mViewWidth) {
				mTranslate = -mViewWidth;
			}
			mGradientMatrix.setTranslate(mTranslate, 0);
			mLinearGradient.setLocalMatrix(mGradientMatrix);
			postInvalidateDelayed(50);
		}
	}

}