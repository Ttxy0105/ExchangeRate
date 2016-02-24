package com.bloch1790.exchangerate.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ExchangRateOpenHelper extends SQLiteOpenHelper {

    /*
    建表语句
     */
    public static final String CREATE_CURRENCY = "create table Currency(" +
            "id integer primary key autoincrement," +
            "country_name text," +
            "country_code text)";

    public ExchangRateOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CURRENCY);    //创建Currency表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
