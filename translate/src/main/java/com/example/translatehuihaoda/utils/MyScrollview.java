package com.example.translatehuihaoda.utils;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;

/**
 * Time:         2021/1/5
 * Author:       C
 * Description:  MyScrollview
 * on:
 */
public class MyScrollview  extends ScrollView {
    private static final int MAX_Y_OVERSCROLL_DISTANCE=500;
    private Context mContext;
    private int mMaxYOverscrollDistance;
    public MyScrollview(Context context) {
        super(context);
// TODO Auto-generated constructor stub
        mContext = context;
        initBounceScrollView();
    }
    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
        mContext = context;
        initBounceScrollView();
    }
    public MyScrollview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
// TODO Auto-generated constructor stub
        mContext = context;
        initBounceScrollView();
    }
    private void initBounceScrollView()
    {
        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverscrollDistance=(int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }
    protected boolean overScrollBy(int deltaX,int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
//这块是关键性代码
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}