package com.bloch1790.exchangerate.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloch1790.exchangerate.R;

import java.lang.reflect.Field;

public class CurrencyActivity extends Activity {
    private MyLinearLayout view_01;
    private MyLinearLayout view_02;
    private TextView name01;
    private TextView name02;
    private TextView code01;
    private TextView code02;
    private ImageView flag1;
    private ImageView flag2;
    private String name;
    private String code;
    int x1, x2;
    SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_main);
        preferences = getSharedPreferences("country", MODE_PRIVATE);
        editor = preferences.edit();
        initView();
        setListeners();
    }

    private void initView() {
        view_01 = (MyLinearLayout) findViewById(R.id.money_01);
        view_02 = (MyLinearLayout) findViewById(R.id.money_02);
        name01 = (TextView) view_01.findViewById(R.id.name);
        name02 = (TextView) view_02.findViewById(R.id.name);
        code01 = (TextView) view_01.findViewById(R.id.code);
        code02 = (TextView) view_02.findViewById(R.id.code);
        flag1 = (ImageView) view_01.findViewById(R.id.country_flag);
        flag2 = (ImageView) view_02.findViewById(R.id.country_flag);
        String pref_name01 = preferences.getString("name1", null);
        String pref_code01 = preferences.getString("code1", null);
        String pref_name02 = preferences.getString("name2", null);
        String pref_code02 = preferences.getString("code2", null);
        if (pref_name02 != null && pref_code02 != null) {
            name02.setText(pref_name02);
            code02.setText(pref_code02);
            flag2.setImageResource(getResourceByReflect(pref_code02.toLowerCase()));
        } else {
            name02.setText("美元");
            code02.setText("USD");
            flag2.setImageResource(R.drawable.usd);
        }
        if (pref_name01 != null && pref_code01 != null) {
            name01.setText(pref_name01);
            code01.setText(pref_code01);
            flag1.setImageResource(getResourceByReflect(pref_code01.toLowerCase()));
        }
    }

    private void setListeners() {

        view_01.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = (int) event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = (int) event.getX();
                    if (x2 - x1 > 300) {
                        Log.i("TAG", x2 - x1 + "移动距离");
                        Intent intent = new Intent(CurrencyActivity.this, CountryActivity.class);
                        startActivityForResult(intent, 1);
                    }
                }
                return false;
            }
        });
        view_02.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = (int) event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = (int) event.getX();
                    if (x2 - x1 > 300) {
                        Log.i("TAG", x2 - x1 + "移动距离");
                        Intent intent = new Intent(CurrencyActivity.this, CountryActivity.class);
                        startActivityForResult(intent, 2);
                    }
                }
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    name = data.getStringExtra("name");
                    //Logger.i("传回了" + name);
                    name01.setText(name);
                    code = data.getStringExtra("code");
                    code01.setText(code);
                    flag1.setImageResource(getResourceByReflect(code.toLowerCase()));
                    editor.putString("name1", name);
                    editor.putString("code1", code);
                    editor.commit();
                    //Logger.i("传回了" + code);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    name = data.getStringExtra("name");
                    name02.setText(name);
                    code = data.getStringExtra("code");
                    code02.setText(code);
                    flag2.setImageResource(getResourceByReflect(code.toLowerCase()));
                    editor.putString("name2", name);
                    editor.putString("code2", code);
                    editor.commit();
                }
                break;
        }
    }

    /**
     * 通过图片名获得图片资源id
     *
     * @param imageName
     * @return 图片资源id
     */
    public int getResourceByReflect(String imageName) {
        Class drawable = R.drawable.class;
        Field field = null;
        int r_id = 0;
        try {
            field = drawable.getField(imageName);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            Log.e("ERROR", "PICTURE NOT　FOUND！");
        }
        return r_id;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
