package com.bloch1790.exchangerate.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bloch1790.exchangerate.model.Country;
import com.bloch1790.exchangerate.util.Pinyin4jUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
            values.put("country_name", country.getName());
            values.put("country_code", country.getCode());
            db.insert("Currency", null, values);
        }
//        Log.i("TAG","第一次存储");
    }

    /**
     * 从数据库读取货币
     */
    public List<Country> loadCountry() {
        List<Country> list = new ArrayList<Country>();
        Cursor cursor = db.query(true, "Currency", new String[]{"id", "country_name", "country_code"}, null, null, "country_name", null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCode(cursor.getString(cursor.getColumnIndex("country_code")));
//                Log.i("TAG", country.getName());
                list.add(country);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        Log.i("TAG", "数据库读取成功");
        Collections.sort(list, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                String name01 = c1.getName();
                String name02 = c2.getName();
                char firChar = Pinyin4jUtil.converterToFirstSpell(name01).charAt(0);
                char secChar = Pinyin4jUtil.converterToFirstSpell(name02).charAt(0);
                if (firChar < secChar) {
                    return -1;
                } else if (firChar > secChar) {
                    return 1;
                } else
                    return 0;
            }
        });
        return list;
    }
}
