package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.internal.ViewUtils;

public class Button extends AppCompatActivity {


    private RelativeLayout rl_root;

    private EditText et;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button1);
  /*      rl_root=findViewById(R.id.rl_root);
        et=findViewById(R.id.et);



        //显示隐藏键盘上的额三个按钮
        et.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                Button.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = Button.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                System.out.println(screenHeight+":"+r.bottom);
                Log.d("Keyboard Size", "Size: " + heightDifference);
                showAViewOverKeyBoard(heightDifference);
            }

        });



    }


    private void showAViewOverKeyBoard(int heightDifference) {
        if (heightDifference > 0) {//显示
            if (view == null) {//第一次显示的时候创建  只创建一次
                view = View.inflate(this, R.layout.item, null);
                RelativeLayout.LayoutParams loginlayoutParams = new RelativeLayout.LayoutParams(-1, -2);
                loginlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                loginlayoutParams.bottomMargin = heightDifference;
                rl_root.addView(view, loginlayoutParams);
            }
            view.setVisibility(View.VISIBLE);
        } else {//隐藏
            if (view != null) {
                view.setVisibility(View.GONE);
            }
        }
    }*/
//    private void showAViewOverKeyBoard(int heightDifference) {
//        if (view == null) {
//            view = View.inflate(this, R.layout.item, null);
//        }
//        RelativeLayout.LayoutParams loginlayoutParams = new RelativeLayout.LayoutParams(-1, -2);
//        loginlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        loginlayoutParams.bottomMargin = heightDifference;
//        rl_root.removeView(view);
//        if (heightDifference > 0) {
//            rl_root.addView(view, loginlayoutParams);
//        }
   }
}