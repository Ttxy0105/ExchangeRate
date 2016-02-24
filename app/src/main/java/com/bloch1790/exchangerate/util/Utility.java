package com.bloch1790.exchangerate.util;

import android.text.TextUtils;
import android.util.Log;

import com.bloch1790.exchangerate.db.ExchangeRateDB;
import com.bloch1790.exchangerate.model.Country;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class Utility {
    /**
     * 解析和处理返回的数据
     */
    public synchronized static boolean handleResponse(ExchangeRateDB exchangeRateDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getInt("error_code")==0) {
                    JSONObject list_01 = jsonObject.getJSONObject("result");
                    JSONArray array = list_01.getJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Country country = new Country();
                        country.setName(object.getString("name"));
                        country.setCode(object.getString("code"));
                        Log.i("TAG",object.getString("name"));
                        exchangeRateDB.saveCountry(country);
                    }
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
