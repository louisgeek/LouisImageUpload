package com.louisgeek.httplib.callback;

import android.util.Log;

/**
 * Created by louisgeek on 2016/7/12.
 */
public abstract class SimpleFileOkHttpCallback extends FileOkHttpCallback{
    private static final String TAG = "SimpleFile_Callback";

    public abstract void OnSuccess(String filePath, int statusCode);
    @Override
    public void OnSuccessNotifyUI(String filePath, int statusCode) {
        Log.d(TAG, "OnSuccessNotifyUI: filePath:"+filePath);
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
