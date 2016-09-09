package com.louisgeek.httplib.callback;

import android.util.Log;

/**
 * Created by louisgeek on 2016/7/11.
 */
public abstract class SimpleStringOkHttpCallback extends StringOkHttpCallback{
    private static final String TAG = "SimpleString_Callback";

    public abstract void OnSuccess(String result, int statusCode);
    @Override
    public void OnSuccessNotifyUI(String result, int statusCode) {
        Log.d(TAG, "OnSuccessNotifyUI: result:"+result);
    }

    @Override
    public void OnError(String errorMsg, int statusCode) {
        Log.d(TAG, "OnError: errorMsg:"+errorMsg);
    }

    @Override
    public void OnErrorNotifyUI(String errorMsg, int statusCode) {
        Log.d(TAG, "OnErrorNotifyUI: errorMsg:"+errorMsg);
    }
}
