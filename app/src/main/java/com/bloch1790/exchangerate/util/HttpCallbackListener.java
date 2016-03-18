package com.bloch1790.exchangerate.util;


public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);

}
