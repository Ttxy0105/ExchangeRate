package com.bloch1790.exchangerate.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.bloch1790.exchangerate.R;


public class MyLinearLayout extends LinearLayout {



    private Scroller scroller = new Scroller(getContext());


    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.currency_item, this);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        /*Logger.i("距离", oldLeft + "oldLeft");
        Logger.i("距离", oldTop + "oldTop");
        Logger.i("距离", oldRight + "oldRight");
        Logger.i("距离", oldBottom + "oldBottom");*/
        int save = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = rawX;
                lastY = rawY;
                /*Logger.i("距离", rawX + "");
                Logger.i("距离", rawY + "");
                Logger.i("距离", lastX + "");
                Logger.i("距离", lastY + "");*/
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
                    /*Logger.i("移动", rawX + "rawX");
                    Logger.i("移动", lastX + "lastX");*/
                }
            case MotionEvent.ACTION_UP:
                /*Logger.i("距离1", getLeft() + "oldLeft");
                Logger.i("距离1", getTop() + "oldTop");
                Logger.i("距离", getRight() + "oldRight");
                Logger.i(getBottom() + "oldBottom", "距离");*/
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
