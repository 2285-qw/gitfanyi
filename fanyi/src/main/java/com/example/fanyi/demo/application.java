package com.example.fanyi.demo;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * Time:         2020/12/31
 * Author:       C
 * Description:  application
 * on:
 */
public class application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //调试 log
        UMConfigure.setLogEnabled(true);
        //初始化sdk
        UMConfigure.init(this,"5fec3edaadb42d582695f09e", "yunhao", UMConfigure.DEVICE_TYPE_PHONE,null);
        //页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
