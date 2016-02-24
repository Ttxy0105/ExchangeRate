package com.bloch1790.exchangerate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.bloch1790.exchangerate.R;
import com.bloch1790.exchangerate.adapter.CountryAdapter;
import com.bloch1790.exchangerate.db.ExchangeRateDB;
import com.bloch1790.exchangerate.model.Country;
import com.bloch1790.exchangerate.util.HttpCallbackListener;
import com.bloch1790.exchangerate.util.HttpUtil;
import com.bloch1790.exchangerate.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class CountryActivity extends Activity {
    private ListView listView;
    private CountryAdapter adapter;
    private List<Country> country_list = new ArrayList<>();
    private List<Country> data_list = new ArrayList<>();
    private ExchangeRateDB exchangeRateDB;
    private String code = "list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countrylayout);
        exchangeRateDB = ExchangeRateDB.getInstance(this);
        queryList(code);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CountryAdapter(this, data_list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void queryList(final String code) {
        Log.i("TAG","查询");
        country_list = exchangeRateDB.loadCountry();
        Log.i("TAG","Size="+country_list.size()+"");
        if (country_list.size() == 0) {
            HttpUtil.sendHttpRequest(HttpUtil.ADDRESS + code + HttpUtil.KEY, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    boolean result = false;
                    result = Utility.handleResponse(exchangeRateDB, response);
                    if (result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("TAG", "再次运行");
                                queryList(code);
                            }
                        });
                    }

                }

                @Override
                public void onError(Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CountryActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }else if (country_list.size() > 0) {
            Log.i("TAG", "刷新");
            data_list.clear();
            for (Country country : country_list) {
                data_list.add(country);
            }
            adapter.notifyDataSetChanged();
        }

    }
}
