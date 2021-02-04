package com.example.translatehuihaoda.utils;

import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.Date;

//表名
@Table("sq_translate")
public class TestModel {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @NotNull
    private int id;

    //翻译原文
    @NotNull
    private String tr_from;

    //翻译译文
    @NotNull
    private String tr_to;

    //翻译时间
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTr_from() {
        return tr_from;
    }

    public void setTr_from(String tr_from) {
        this.tr_from = tr_from;
    }

    public String getTr_to() {
        return tr_to;
    }

    public void setTr_to(String tr_to) {
        this.tr_to = tr_to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
