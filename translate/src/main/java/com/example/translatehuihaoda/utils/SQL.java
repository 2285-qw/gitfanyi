package com.example.translatehuihaoda.utils;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.translatehuihaoda.application.BaseApplication.liteOrm;


public class SQL {
   public static List<TestModel> list;

   public  static void sql_add(String from,String to){
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
       //获取当前时间
       Date date = new Date(System.currentTimeMillis());

       TestModel testModel=new TestModel();

       testModel.setTr_from(from);
       testModel.setTr_to(to);
       testModel.setDate(date);
       liteOrm.insert(testModel);


   }

    public static void sql_translate(){
    list=liteOrm.query(TestModel.class);
        Collections.reverse(list);
        for (int i=0;i<list.size();i++){
        list.get(i).getId();
        list.get(i).getDate();
        list.get(i).getTr_from();
        list.get(i).getTr_to();

        //System.out.println( list.get(i).getId()+":"+list.get(i).getDate()+":"+list.get(i).getTr_from()+":"+list.get(i).getTr_to());

    }
    }
}
