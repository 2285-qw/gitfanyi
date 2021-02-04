package com.example.spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private HashMap si_Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner);


        //Spinner实现
        initSpinner();
    }

    private void initSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("自动识别");
        data_list.add("中文");
        data_list.add("英语");
        data_list.add("日语");
        System.out.println(spinner.getBaseline());

        //常见语种代码集合
        si_Map=new HashMap();
        si_Map.put("自动识别","auto");
        si_Map.put("中文","zh");
        si_Map.put("英语","en");
        si_Map.put("日语","jp");



        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取当前选中的Spinner
                String sp_text= data_list.get(position);
                System.out.println(si_Map.get(sp_text));
                System.out.println(sp_text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}