package com.example.translatehuihaoda.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.translatehuihaoda.R;

import static com.example.translatehuihaoda.utils.SQL.list;


public class lv_Adapter extends BaseAdapter {

    private final Context context;

    public lv_Adapter(Context context) {
        this.context = context;
    }

    private TextView t_lv1;
    private TextView t_lv2;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.lv_item,null);
            t_lv1=convertView.findViewById(R.id.t_lv1);
            t_lv1.setText(list.get(position).getTr_from());
            t_lv2=convertView.findViewById(R.id.t_lv2);
            t_lv2.setText(list.get(position).getTr_to());
            UtilTools.setFont(context,t_lv1,"fonts/DIN-Regular.otf");
            UtilTools.setFont(context,t_lv2,"fonts/DIN-Regular.otf");
            return convertView;
        }else {
            view=convertView;
            t_lv1=view.findViewById(R.id.t_lv1);
            t_lv1.setText(list.get(position).getTr_from());
            t_lv2=view.findViewById(R.id.t_lv2);
            t_lv2.setText(list.get(position).getTr_to());
            return view;
        }


    }
}
