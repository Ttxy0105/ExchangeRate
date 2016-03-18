package com.bloch1790.exchangerate.util;

import android.text.TextUtils;
import android.util.Log;

import com.bloch1790.exchangerate.db.ExchangeRateDB;
import com.bloch1790.exchangerate.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utility {
    /**
     * 解析和处理返回的数据
     */
    public synchronized static boolean handleResponse(ExchangeRateDB exchangeRateDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("error_code") == 0) {
                    JSONObject list_01 = jsonObject.getJSONObject("result");
                    JSONArray array = list_01.getJSONArray("list");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Country country = new Country();
                        country.setName(object.getString("name"));
                        country.setCode(object.getString("code"));
                        exchangeRateDB.saveCountry(country);
                    }
                    Log.i("TAG", "数据库存储成功");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public synchronized static String handleResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                String exchange;
                JSONObject object = new JSONObject(response);
                if (object.getInt("error_code") == 0) {
                    JSONArray result = object.getJSONArray("result");
                    JSONObject jsonObject = result.getJSONObject(0);
                    exchange = jsonObject.getString("result");
                    Log.i("TAG", exchange);
                    return exchange;
                } else {
                    System.out.println(object.get("error_code") + ":" + object.get("reason"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void getRequest3(String code01, String code02) {
        String url = "http://op.juhe.cn/onebox/exchange/currency" + HttpUtil.KEY + "&from=" + code01 + "&to=" + code02;//请求接口地址
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                JSONObject object = null;
                try {
                    String exchange;
                    object = new JSONObject(response);
                    if (object.getInt("error_code") == 0) {
                        JSONArray result = object.getJSONArray("result");
                        JSONObject jsonObject = result.getJSONObject(0);
                        exchange = jsonObject.getString("result");
                        Log.i("TAG", exchange);
                    } else {
                        System.out.println(object.get("error_code") + ":" + object.get("reason"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }

        });
    }

    /*public synchronized static String getRequest2(String code01, String code02) {
        String url = "http://op.juhe.cn/onebox/exchange/currency" + HttpUtil.KEY + "&from=" + code01 + "&to=" + code02;//请求接口地址
        String response = HttpUtil.sendHttpRequest(url);
        JSONObject object = null;
        //if (!TextUtils.isEmpty(response)) {
            try {
                String exchange;
                object = new JSONObject(response);
                if (object.getInt("error_code") == 0) {
                    JSONArray result = object.getJSONArray("result");
                    JSONObject jsonObject = result.getJSONObject(0);
                    exchange = jsonObject.getString("result");
                    Log.i("TAG", exchange);
                    return exchange;
                } else {
                    System.out.println(object.get("error_code") + ":" + object.get("reason"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}
        return null;
    }*/


}
