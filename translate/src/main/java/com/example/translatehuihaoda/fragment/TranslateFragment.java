package com.example.translatehuihaoda.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.example.translatehuihaoda.R;
import com.example.translatehuihaoda.config.TTAdManagerHolder;
import com.example.translatehuihaoda.utils.BannerUtil;
import com.example.translatehuihaoda.utils.SQL;
import com.example.translatehuihaoda.utils.StaticClass;
import com.example.translatehuihaoda.utils.TransApi;
import com.example.translatehuihaoda.utils.UtilTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Time:         2020/12/23
 * Author:       C
 * Description:  TranslateFragment
 * on:翻译页面
 */
public class TranslateFragment extends Fragment implements View.OnClickListener {

    private EditText text;
    //翻译按钮
    private Button button;
    //取消按钮
    private Button bt_cancel;
    //清空按钮
    private Button bt_clear;
    //页面清除图片按钮
    private Button clear;

    private TextView textView;
    //获取输入框的文本
    private String main_text;

    //复制按钮
    private Button text_copy;

    //底部语言按钮
    private Button bu_1;
    private Button bu_2;

    private RelativeLayout rl_root;

    private Spinner spinner1;
    private Spinner spinner2;
    private List<String> data_list;
    private List<String> data_list2;
    private ArrayAdapter<String> arr_adapter;
    private ArrayAdapter<String> arr_adapter2;
    private HashMap si_Map;

    //源语言
    private String sp_from;
    //翻译语言
    private String sp_to;
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Object re = msg.obj;
                    textView.setText((String) re);
                    break;

            }

        }

    };

    View view;
    View view1;
    private Context mContext;

    //Banner广告布局
    private static FrameLayout mBannerContainer;
    //
    static TTAdNative mTTAdNative;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_translate, null);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity().getBaseContext();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化spinner
        initSpinner();
        //初始化View
        initView();

        //添加广告
        mBannerContainer = view.findViewById(R.id.banner_container1);
    }

    @Override
    public void onResume() {
        super.onResume();


        mTTAdNative = TTAdManagerHolder.get().createAdNative(getActivity());
        //初始化广告
        BannerUtil.loadBannerAd(StaticClass.BANNERID, mTTAdNative, getActivity(), mBannerContainer);


        //显示隐藏键盘上的三个按钮
        text.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //fragment的高度
                int measuredHeight = view.getMeasuredHeight();
                //获取当前界面可视部分
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getActivity().getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数

                if (hasNavBar(mContext)) {
                    int heightDifference = screenHeight - r.bottom - getNavigationBarHeight(mContext);
                    System.out.println(measuredHeight + ":" + r.bottom + ":" + hasNavBar(mContext));
                    Log.d("Keyboard Size", "Size: " + heightDifference);
                    showAViewOverKeyBoard(heightDifference);
                } else {
                    int heightDifference = screenHeight - r.bottom;
                    System.out.println(measuredHeight + ":" + r.bottom + ":" + hasNavBar(mContext));
                    Log.d("Keyboard Size", "Size: " + heightDifference);
                    showAViewOverKeyBoard(heightDifference);
                }

            }

        });


    }

    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }


    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    private void showAViewOverKeyBoard(int heightDifference) {
        RelativeLayout buttom_button = view.findViewById(R.id.buttom_button);
        if (heightDifference > 0) {//显示
            if (view1 == null) {//第一次显示的时候创建  只创建一次
                view1 = View.inflate(mContext, R.layout.bt_item1, null);
                RelativeLayout.LayoutParams loginlayoutParams = new RelativeLayout.LayoutParams(-1, -2);
                loginlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                loginlayoutParams.bottomMargin = 0;
                rl_root.addView(view1, loginlayoutParams);

                //翻译按钮
                button = view1.findViewById(R.id.button3);
                button.setOnClickListener(this);
                bt_cancel = view1.findViewById(R.id.button1);
                bt_cancel.setOnClickListener(this);
                bt_clear = view1.findViewById(R.id.button2);
                bt_clear.setOnClickListener(this);

            }
            view1.setVisibility(View.VISIBLE);
            //隐藏底部RelativeLayout
            buttom_button.setVisibility(View.GONE);
        } else {//隐藏
            if (view1 != null) {
                view1.setVisibility(View.GONE);
                //显示底部RelativeLayout
                buttom_button.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        bu_1 = view.findViewById(R.id.bu_1);
        bu_2 = view.findViewById(R.id.bu_2);

        text = view.findViewById(R.id.text);
        textView = view.findViewById(R.id.textView);
        rl_root = view.findViewById(R.id.rl_root);
        text_copy = view.findViewById(R.id.copy);
        text_copy.setOnClickListener(this);
        clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(this);

        UtilTools.setFont(mContext, textView, "fonts/DIN-Medium.otf");
        UtilTools.setFont(mContext, text, "fonts/DIN-Medium.otf");
    }

    //判断网络连接是否正常
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private void inTranslate(String query) {
        if (isNetworkConnected(mContext)) {
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    TransApi transApi = new TransApi("20201214000647120", "sZ8fYFGkFXkgkpxLsoj2");
                    String transResult = transApi.getTransResult(query, sp_from, sp_to);

                    System.out.println(transResult);

                    try {
                        JSONObject JSON = new JSONObject(transResult);
                        JSONArray opt = JSON.optJSONArray("trans_result");

                        JSONObject jsonby = opt.getJSONObject(0);
                        //获得译文
                        Object opt2 = jsonby.optString("dst");
                        //System.out.println(opt2);

                        Log.d("TAG", (String) opt2);
                        //将数据存入数据库
                        SQL.sql_add(query, (String) opt2);
                        //刷新list集合
                        SQL.sql_translate();
                        //返回数据修改UI
                        Message message = new Message();
                        message.what = 1;
                        message.obj = opt2;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        } else {
            Toast.makeText(mContext, "请检查网络连接是否正常", Toast.LENGTH_SHORT).show();
        }
    }

    //Spinner实现
    private void initSpinner() {
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);

        View view2 = View.inflate(mContext, R.layout.simple_spinner_item, null);
        TextView text1 = view2.findViewById(R.id.text1);
        UtilTools.setFont(mContext, text1, "fonts/FONT.TTF");

        //spinner1数据
        data_list = new ArrayList<String>();
        data_list.add("自动识别");
        data_list.add("中文");
        data_list.add("英语");
        data_list.add("日语");
        System.out.println(spinner1.getBaseline());

        //spinner2数据
        data_list2 = new ArrayList<String>();
        data_list2.add("英语");
        data_list2.add("中文");
        data_list2.add("日语");
        spinner2.getBaseline();

        //常见语种代码集合
        si_Map = new HashMap();
        si_Map.put("自动识别", "auto");
        si_Map.put("中文", "zh");
        si_Map.put("英语", "en");
        si_Map.put("日语", "jp");


        //spinner1适配器
        arr_adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner1.setAdapter(arr_adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取当前选中的Spinner
                String sp_text = data_list.get(position);
                sp_from = (String) si_Map.get(sp_text);
                bu_1.setText(sp_text);
                //System.out.println(sp_from);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner2适配器
        arr_adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, data_list2);
        arr_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner2.setAdapter(arr_adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取当前选中的Spinner
                String sp_text = data_list2.get(position);
                sp_to = (String) si_Map.get(sp_text);
                bu_2.setText(sp_text);
                //System.out.println(sp_to);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                if (!String.valueOf(text.getText()).isEmpty()) {
                    String s = String.valueOf(text.getText()).replaceAll("\r|\n", " ");
                    inTranslate(s);
                    Log.d("TAG", s);
                    //隐藏键盘
                    UtilTools.Hide_key(getActivity());
                } else {
                    Toast.makeText(mContext, "输入框不为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button1:
                //隐藏键盘
                UtilTools.Hide_key(getActivity());
                break;
            case R.id.button2:
            case R.id.clear:
                text.setText(null);
                textView.setText(null);
                break;
            case R.id.copy:
                if (!textView.getText().toString().trim().isEmpty()) {
                    UtilTools.text_copy(getActivity(), textView.getText().toString().trim());
                    Toast.makeText(mContext, "复制成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "没有可复制的文本", Toast.LENGTH_SHORT).show();
                }
                break;


        }

    }
}
