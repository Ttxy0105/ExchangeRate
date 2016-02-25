package com.bloch1790.exchangerate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloch1790.exchangerate.R;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_main);
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
    }

    private void setListeners() {
        view_01.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                int x1 = 0, x2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = (int) event.getX();

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = (int) event.getX();
                    if (x2 - x1 > 300) {
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
                int x1 = 0, x2;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = (int) event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = (int) event.getX();
                    if (x2 - x1 > 300) {
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
                    Log.i("TAG", "传回了" + name);
                    name01.setText(name);
                    code = data.getStringExtra("code");
                    code01.setText(code);
                    Log.i("TAG", "传回了" + code);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    name = data.getStringExtra("name");
                    name02.setText(name);
                    Log.i("TAG", "传回了" + name);
                    code = data.getStringExtra("code");
                    code02.setText(code);
                    Log.i("TAG", "传回了" + code);
                }
                break;
        }
    }
}
