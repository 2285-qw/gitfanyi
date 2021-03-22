
package com.example.translatehuihaoda.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.translatehuihaoda.MainActivity;
import com.example.translatehuihaoda.utils.StaticClass;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.translatehuihaoda.R;
import com.example.translatehuihaoda.utils.shareUtils;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    startActivity(new Intent(SplashActivity.this, SplashActivityAD.class));
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_splay);

        if(isFirst()){
            showSecurityDialog();
        }else {
            //延时2秒发送出去
            handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        }

        //权限申请
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0x11);

    }

    //禁止返回键
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private void showSecurityDialog() {
        //TODO 显示提醒对话框
        Dialog securityDialog = new Dialog(this);
        securityDialog.setCancelable(false);//返回键也会屏蔽
        securityDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(this, R.layout.dialog_activity_sercurity, null);
        TextView tv_msg = view.findViewById(R.id.sercurity_tv_msgnotice);
        TextView tv_cancel = view.findViewById(R.id.sercurity_tv_cancel);
        TextView tv_positive = view.findViewById(R.id.sercurity_tv_positive);

        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_msg.getText());
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#C89C3C")), 100, 106, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(new TextClick(), 100, 106, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setText(spannable);

        SpannableStringBuilder spannable1 = new SpannableStringBuilder(tv_msg.getText());
        spannable1.setSpan(new ForegroundColorSpan(Color.parseColor("#C89C3C")), 107, 113, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setMovementMethod(LinkMovementMethod.getInstance());
        spannable1.setSpan(new TextClick1(), 107, 113, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setText(spannable1);

        tv_msg.setHighlightColor(Color.parseColor("#ffffff"));

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityDialog.dismiss();
                shareUtils.putBoolean(SplashActivity.this,StaticClass.SPLASH_IS_FIRST,true);
                finish();
            }
        });
        tv_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityDialog.dismiss();
                //TODO 进入主界面
                shareUtils.putBoolean(SplashActivity.this,StaticClass.SPLASH_IS_FIRST,false);
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });

        securityDialog.setContentView(view);
        securityDialog.show();
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) { //在此处理点击事件
            WebViewactivity.openActivity(getBaseContext(),"http://huihaoda.cn/yinsi/fy.html");
            //startActivity(new Intent(SplashActivity.this,HideActivity.class));
            //Log.e("eeee_click", "点击");
            //TODO 点击事件处理
        }
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(Color.parseColor("#C89C3C"));
        }
    }

    private class TextClick1 extends ClickableSpan {
        @Override
        public void onClick(View widget) { //在此处理点击事件
            WebViewactivity.openActivity(getBaseContext(),"http://huihaoda.cn/yhxy/fs.html");
            //startActivity(new Intent(SplashActivity.this,User_agreementActivity.class));
            //Log.e("eeee_click", "点击");
            //TODO 点击事件处理
        }
        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(Color.parseColor("#C89C3C"));
        }
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        Boolean isFirst = shareUtils.getBoolean(this, StaticClass.SPLASH_IS_FIRST, true);
        if (isFirst) {
            //是第一次运行
            return true;
        } else {
            return false;
        }
    }
}