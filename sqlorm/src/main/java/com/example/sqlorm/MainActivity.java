package com.example.sqlorm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.sqlorm.utils.SQL;
import com.example.sqlorm.utils.lv_Adapter;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQL.sql_translate();

        ListView listView=findViewById(R.id.lv_history_translate);
        listView.setDividerHeight(0);
        listView.setAdapter(new lv_Adapter(this));

    }
}