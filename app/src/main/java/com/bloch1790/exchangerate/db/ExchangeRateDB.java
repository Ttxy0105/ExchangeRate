package com.bloch1790.exchangerate.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bloch1790.exchangerate.model.Country;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "exchange_rate";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static ExchangeRateDB exchangeRateDB;
    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private ExchangeRateDB(Context context) {
        ExchangRateOpenHelper dbHelper = new ExchangRateOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取ExchangeRateDB的实例
     */
    public synchronized static ExchangeRateDB getInstance(Context context) {
        if (exchangeRateDB == null) {
            exchangeRateDB = new ExchangeRateDB(context);
        }
        return exchangeRateDB;
    }

    /**
     * 将Country实例存储到数据库
     */
    public void saveCountry(Country country) {
        if (country != null) {
            ContentValues values = new ContentValues();
            values.put("country_name",country.getName());
            values.put("country_code",country.getCode());
            db.insert("Country", null, values);
        }
    }

    /**
     * 从数据库读取货币
     */
    public List<Country> loadCountry() {
        List<Country> list = new ArrayList<Country>();
        Cursor cursor = db.query("Country", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCode(cursor.getString(cursor.getColumnIndex("country_code")));
                list.add(country);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
