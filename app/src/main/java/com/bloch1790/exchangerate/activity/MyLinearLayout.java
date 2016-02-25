package com.bloch1790.exchangerate.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.bloch1790.exchangerate.R;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public class MyLinearLayout extends LinearLayout {


    private EditText number;
    private Scroller scroller = new Scroller(getContext());
    private int screenWidth;
    //    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                Intent intent = new Intent(getContext(), CountryActivity.class);
//                getContext().startActivity(intent);
//            }
//        }
//    };
    public static final int MOVE_SUCCESSS = 1;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.currency_item, this);
        initView();
    }

    private void initView() {
        number = (EditText) findViewById(R.id.count);
        screenWidth = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }



    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    scroller.getCurrX(),
                    0);
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }



    private int lastX;
    private int lastY;


    // 绝对坐标方式
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) (event.getRawX());
        int rawY = (int) (event.getRawY());
        int oldLeft = getLeft();
        int oldTop = getTop();
        int oldRight = getRight();
        int oldBottom = getBottom();
        Log.i("距离", oldLeft + "oldLeft");
        Log.i("距离", oldTop + "oldTop");
        Log.i("距离", oldRight + "oldRight");
        Log.i("距离", oldBottom + "oldBottom");
        int save = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = rawX;
                lastY = rawY;
                Log.i("距离", rawX + "");
                Log.i("距离", rawY + "");
                Log.i("距离", lastX + "");
                Log.i("距离", lastY + "");
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = rawX - lastX;
                save = offsetX;
                Log.i("offsetX", offsetX + "offsetX");
                if (offsetX > 300) {
                    // 在当前left、top、right、bottom的基础上加上偏移量
                    layout(getLeft() + offsetX,
                            getTop() + 0,
                            getRight() + offsetX,
                            getBottom() + 0);
                    // 重新设置初始坐标
                    lastX = rawX;
                    lastY = rawY;
                    Log.i("移动", rawX + "rawX");
                    Log.i("移动", lastX + "lastX");
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("距离1", getLeft() + "oldLeft");
                Log.i("距离1", getTop() + "oldTop");
                Log.i("距离", getRight() + "oldRight");
                Log.i("距离", getBottom() + "oldBottom");
                layout(oldLeft,
                        oldTop,
                        oldRight,
                        oldBottom);
                invalidate();
                break;
        }

        return true;
    }

}
