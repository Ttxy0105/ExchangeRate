package com.bloch1790.exchangerate.util;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
