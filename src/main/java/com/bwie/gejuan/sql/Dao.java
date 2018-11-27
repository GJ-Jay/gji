package com.bwie.gejuan.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {

    private final SQLiteDatabase db;

    public Dao(Context context) {
        MyHelper myHelper = new MyHelper(context);
        db = myHelper.getWritableDatabase();
    }

    public long insert(String table, String nullColumnHack, ContentValues values){
        return db.insert(table,nullColumnHack,values);
    }

    public  long delete(String table, String whereClause, String[] whereArgs){
        return db.delete(table,whereClause,whereArgs);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy){
        return db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
    }
}
