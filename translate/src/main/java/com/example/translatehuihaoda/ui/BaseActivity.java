package com.example.translatehuihaoda.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * Time:         2020/12/22
 * Author:       C
 * Description:  BaseActivity
 * on:           公共的Activity
 */
public class BaseActivity extends FragmentActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

    }


}
