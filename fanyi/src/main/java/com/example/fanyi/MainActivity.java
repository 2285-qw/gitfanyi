package com.example.fanyi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.security.identity.MessageDecryptionException;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fanyi.demo.MD5;
import com.example.fanyi.demo.TransApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText text;
    private Button button;
    private TextView textView;
    //获取输入框的文本
    private String main_text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //请求动态权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0x11);


        //初始化spinner
        initSpinner();
        //初始化View
        initView();

        showSecurityDialog();

    }

    private void initView() {
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        //翻译按钮
        button.setOnClickListener(this);

    }

    private void inTranslate(String query) {
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
                    System.out.println(opt2);

                    Message message = new Message();
                    message.what = 1;
                    message.obj = opt2;
                    handler.sendMessage(message);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                inTranslate(String.valueOf(text.getText()));

        }

    }


    //Spinner实现
    private void initSpinner() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

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
        arr_adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner1.setAdapter(arr_adapter);
        spinner1.setBackgroundColor(0);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取当前选中的Spinner
                String sp_text = data_list.get(position);
                sp_from = (String) si_Map.get(sp_text);
                System.out.println(sp_from);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner2适配器
        arr_adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list2);
        arr_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner2.setAdapter(arr_adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取当前选中的Spinner
                String sp_text = data_list2.get(position);
                sp_to = (String) si_Map.get(sp_text);
                System.out.println(sp_to);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        spannable1.setSpan(new TextClick(), 107, 113, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setText(spannable1);


        tv_msg.setHighlightColor(Color.parseColor("#00ffffff"));

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityDialog.dismiss();
                finish();
            }
        });
        tv_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityDialog.dismiss();
                //TODO 进入主界面
            }
        });

        securityDialog.setContentView(view);
        securityDialog.show();
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) { //在此处理点击事件
            Log.e("eeee_click", "点击");
            //TODO 点击事件处理
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(Color.parseColor("#C89C3C"));
        }
    }


}