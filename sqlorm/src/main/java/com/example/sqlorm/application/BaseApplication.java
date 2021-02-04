package com.example.sqlorm.application;

import android.app.Application;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;

public class BaseApplication extends Application {
    public static LiteOrm liteOrm;
    @Override
    public void onCreate() {
        super.onCreate();


        //创建数据库
        if (liteOrm == null) {
            DataBaseConfig config = new DataBaseConfig(this, "translatehuihaoda.db");
            //"liteorm.db"是数据库名称，名称里包含路径符号"/"则将数据库建立到该路径下，可以使用sd卡路径。 不包含则在系统默认路径下创建DB文件。
            //例如 public static final String DB_NAME = SD_CARD + "/lite/orm/liteorm.db";     DataBaseConfig config = new DataBaseConfig(this, DB_NAME);
            config.dbVersion = 1; // set database version
            config.onUpdateListener = null; // set database update listener
            //独立操作，适用于没有级联关系的单表操作，
            liteOrm = LiteOrm.newSingleInstance(config);
            //级联操作,适用于多表级联操作
            // liteOrm=LiteOrm.newCascadeInstance(config); 
        }
        liteOrm.setDebugged(true); // open the log
    }
}
