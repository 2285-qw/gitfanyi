package com.example.translatehuihaoda.ui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Time:         2021/3/22
 * Author:       C
 * Description:  BaseWebView
 * on:
 */
public class BaseWebView extends WebView {
    private ProgressBar mProgressBar;
    public BaseWebView(@NonNull Context context) {
        super(context);
    }

    public BaseWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
        mProgressBar.setMax(100);  //设置加载进度最大值
    }
}
