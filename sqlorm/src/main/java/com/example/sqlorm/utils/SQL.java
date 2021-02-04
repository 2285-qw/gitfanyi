package com.example.sqlorm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.sqlorm.utils.TestModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.sqlorm.application.BaseApplication.liteOrm;

public class SQL {
   public static List<TestModel> list;

    public static void sql_translate(){

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    //获取当前时间
    Date date = new Date(System.currentTimeMillis());

//    TestModel testModel=new TestModel();
//
//        testModel.setTr_from("你好");
//        testModel.setTr_to("hello");
//        testModel.setDate(date);
//        liteOrm.insert(testModel);
//
//    TestModel testMode2=new TestModel();
//        testMode2.setTr_from("你好");
//        testMode2.setTr_to("hello");
//        testMode2.setDate(date);
//        liteOrm.insert(testMode2);


    list=liteOrm.query(TestModel.class);
        for (int i=0;i<list.size();i++){
        list.get(i).getId();
        list.get(i).getDate();
        list.get(i).getTr_from();
        list.get(i).getTr_to();

       // System.out.println( list.get(i).getId()+":"+list.get(i).getDate()+":"+list.get(i).getTr_from()+":"+list.get(i).getTr_to());

    }
    }
}
