package com.example.translatehuihaoda.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.translatehuihaoda.R;
import com.example.translatehuihaoda.utils.SQL;
import com.example.translatehuihaoda.utils.TestModel;
import com.example.translatehuihaoda.utils.UtilTools;
import com.example.translatehuihaoda.utils.lv_Adapter;

import static com.example.translatehuihaoda.application.BaseApplication.liteOrm;

/**
 * Time:         2020/12/23
 * Author:       C
 * Description:  RecentFragment
 * on:历史页面
 */
public class RecentFragment extends Fragment implements View.OnClickListener {
    ListView listView;
    View view;

    private TextView textView;
    //清除历史缓存布局
    private LinearLayout li_del;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_recent,null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //设置字体
        textView=view.findViewById(R.id.textView);
        UtilTools.setFont(getActivity().getBaseContext(),textView,"fonts/DIN-Medium.otf");


        //显示历史记录
        SQL.sql_translate();

        //liteOrm.deleteAll(TestModel.class);
        //清除缓存历史按钮
        Button bu_del=view.findViewById(R.id.del);
        bu_del.setOnClickListener(this);

        //清除历史缓存布局
        li_del=view.findViewById(R.id.li_del);
        li_del.setOnClickListener(this);

        listView=view.findViewById(R.id.lv_history_translate);
       // listView.setDividerHeight(0);
        listView.setAdapter(new lv_Adapter(getActivity().getBaseContext()));

        UtilTools.setFont_button(getActivity().getBaseContext(),bu_del,"fonts/DIN-Light.otf");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_del:
            case R.id.del:
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getActivity());
                //normalDialog.setIcon(R.drawable.buttom_yello);
                normalDialog.setTitle("清除历史记录");
                normalDialog.setMessage("你确定要清除历史记录吗?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //清除历史记录
                                liteOrm.deleteAll(TestModel.class);
                                //显示历史记录
                                SQL.sql_translate();
                                listView.setAdapter(new lv_Adapter(getActivity().getBaseContext()));
                                Toast.makeText(getActivity(),"历史记录已删除！",Toast.LENGTH_LONG).show();
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(),"你已取消！",Toast.LENGTH_LONG).show();
                            }
                        }).show();


                break;
        }
    }
}
