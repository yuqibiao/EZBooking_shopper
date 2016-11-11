package com.yyyu.barbecue.ezbooking_base.ui.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.yyyu.barbecue.ezbooking_base.R;
import com.yyyu.barbecue.ezbooking_base.utils.MyLog;

/**
 * 功能：头部被顶上去的滚动控件
 *
 * 使用：
 * 1.只针对实现了NestedScrollParent接口的View（如RecyclerView）
 * 2.控件的id已经被指定好了,如果需要加控件在ids_sticky_scroll中添加id
 *
 * @author yyyu
 * @date 2016/9/10
 */
public class StickyScrollLayout extends LinearLayout implements NestedScrollingParent {

    private static final String TAG = "StickyScrollLayout";

    private OverScroller mScroller;
    private ViewPager mViewPager;
    private View mIndicator;
    private View mHeader;
    private int  mHeaderViewHeight;
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    public StickyScrollLayout(Context context) {
        this(context, null);
    }

    public StickyScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mScroller = new OverScroller(context);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //MyLog.d(TAG , "onFinishInflate==============");
        mHeader = findViewById(R.id.id_sticky_scroll_header);
        mIndicator = findViewById(R.id.id_sticky_scroll_indicator);
        View view = findViewById(R.id.id_sticky_scroll_viewpager);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException("id_sticky_scroll_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //不限制顶部的高度
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mIndicator.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth() ,
                mHeader.getMeasuredHeight()+mIndicator.getMeasuredHeight()+mViewPager.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeaderViewHeight = mHeader.getMeasuredHeight();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mHeaderViewHeight) {
            y = mHeaderViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mHeaderViewHeight);
        invalidate();
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy>0 && getScrollY() <mHeaderViewHeight;
        boolean showTop = dy<0 && getScrollY()>0&& !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >mHeaderViewHeight ) {
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        //MyLog.e(TAG , "onNestedFling==========");
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        //MyLog.e(TAG , "onNestedScrollAccepted==========");
        nestedScrollingParentHelper.onNestedScrollAccepted(child , target , nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        //MyLog.e(TAG , "onStopNestedScroll==========");
        nestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        MyLog.e(TAG , "onNestedScroll==========");
    }

    @Override
    public int getNestedScrollAxes() {
        MyLog.e(TAG , "getNestedScrollAxes==========");
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }

}
