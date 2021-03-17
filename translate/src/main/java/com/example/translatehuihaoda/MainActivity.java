package com.example.translatehuihaoda;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Time:         2020/12/22
 * Author:       C
 * Description:  BaseActivity
 * on:           主页面
 */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.example.translatehuihaoda.config.TTAdManagerHolder;
import com.example.translatehuihaoda.fragment.RecentFragment;
import com.example.translatehuihaoda.fragment.TranslateFragment;
import com.example.translatehuihaoda.ui.BaseActivity;
import com.example.translatehuihaoda.ui.HideActivity;
import com.example.translatehuihaoda.utils.BannerUtil;
import com.example.translatehuihaoda.utils.StaticClass;
import com.google.android.material.tabs.TabLayout;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.example.translatehuihaoda.utils.AppSigning.getSha1;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    //Tablayout
    private TabLayout mtabLayout;
    //ViewPager
    private ViewPager mviewPager;
    //Title
    private List<String> mtitle;
    //fragment
    private List<Fragment> mfragment;
    //log图片
    private ImageView log;
    //背景图片
    private ImageView main_back;
    //图片循环整数
    private int i=0;
    //Banner广告布局
    private FrameLayout mBannerContainer;
    //
    TTAdNative mTTAdNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //保持屏幕竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //初始化数据
        initDate();
        //初始化View
        initView();

        Log.d("sha1",getSha1(this));

        //添加广告
        mBannerContainer=findViewById(R.id.banner_container);
        //step2:创建TTAdNative对象
        mTTAdNative = TTAdManagerHolder.get().createAdNative(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initDate() {


        mtitle=new ArrayList<>();
        mtitle.add(getString(R.string.translate));
        mtitle.add(getString(R.string.Recent));

        mfragment=new ArrayList<>();
        mfragment.add(new TranslateFragment());
        mfragment.add(new RecentFragment());

        log=findViewById(R.id.log);
        log.setOnClickListener(this);

        main_back=findViewById(R.id.main_back);
    }

    private void initView() {
        mtabLayout=findViewById(R.id.mtabLayout);


        mviewPager=findViewById(R.id.mViewPage);
        //预加载
        mviewPager.setOffscreenPageLimit(mfragment.size());
        mtabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //highlight the color of tab text && tab indicator
        mtabLayout.setTabTextColors(Color.parseColor("#B2FFFFFF"), Color.parseColor("#FFFFFF"));

        mviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    log.setVisibility(View.VISIBLE);
                    main_back.setImageDrawable(getResources().getDrawable(R.mipmap.mian_barck1));

                    mBannerContainer.setVisibility(View.GONE);
                }else {
                    log.setVisibility(View.GONE);
                    main_back.setImageDrawable(getResources().getDrawable(R.mipmap.main_r_back));
                    //Banner广告
                    mBannerContainer.setVisibility(View.VISIBLE);
                    BannerUtil.loadBannerAd(StaticClass.BANNERID,mTTAdNative,MainActivity.this,mBannerContainer);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mviewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            //选中的Item
            public Fragment getItem(int position) {
                return mfragment.get(position);
            }

            @Override
            //返回Item的个数
            public int getCount() {
                return mfragment.size();
            }

            @Nullable
            @Override
            //标题
            public CharSequence getPageTitle(int position) {
                return mtitle.get(position);
            }
        });

        //绑定
        mtabLayout.setupWithViewPager(mviewPager);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.log:
                int s[]={R.mipmap.log1, R.mipmap.log2,R.mipmap.log3,R.mipmap.log4,
                        R.mipmap.log5,R.mipmap.log6,R.mipmap.log7,R.mipmap.log8,R.mipmap.log9,R.mipmap.logo};
                log.setImageResource(s[++i%s.length]);
                break;

        }
    }
}