package com.bwie.gejuan.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "months.db", null, 1);
    }

    /*private String title;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table jsontable(id Integer primary key autoincrement," +
                "thumbnail_pic_s varchar(50)," +
                "thumbnail_pic_s02 varchar(50)," +
                "thumbnail_pic_s03 varchar(50)," +
                "title varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
