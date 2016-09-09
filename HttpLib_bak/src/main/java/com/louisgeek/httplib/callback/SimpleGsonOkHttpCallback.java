package com.louisgeek.httplib.callback;

import android.util.Log;

/**
 * Created by louisgeek on 2016/7/11.
 */
public abstract class SimpleGsonOkHttpCallback<T> extends GsonOkHttpCallback<T>{

    private static final String TAG = "SimpleGson_Callback";
    public  abstract T OnSuccess(String result,int statusCode);
    @Override
    public  void OnSuccessNotifyUI(T t){
        Log.d(TAG, "OnSuccessNotifyUI: t:"+t);
    }
    @Override
    public  void OnError(String errorMsg,int statusCode){
        Log.d(TAG, "OnError: errorMsg:"+errorMsg);
    }
    @Override
    public  void OnErrorNotifyUI(String errorMsg,int statusCode){
        Log.d(TAG, "OnErrorNotifyUI: errorMsg:"+errorMsg);
    }
}