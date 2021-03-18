package com.example.translatehuihaoda.ui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

/**
 * Time:         2021/3/18
 * Author:       C
 * Description:  ScrollListview
 * on:
 */
public class ScrollListview extends ListView {
    public ScrollListview(Context context) {
        super(context);
    }

    public ScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollListview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, // 设计一个较大的值和AT_MOST模式
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
