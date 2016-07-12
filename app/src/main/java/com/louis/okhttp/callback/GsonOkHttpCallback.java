package com.louis.okhttp.callback;

import android.util.Log;

import com.louis.okhttp.util.ThreadUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by louisgeek on 2016/7/11.
 */
public abstract class GsonOkHttpCallback<T> implements okhttp3.Callback{

    private static final String TAG = "GsonOkHttpCallback";
    @Override
    public void onFailure(Call call, final IOException e) {
        //0,  -1   自己定义的。。。
        if (call.isCanceled()){
            this.OnError("GsonOkHttpCallback call is canceled,call:"+call.toString()+";message:"+e.getMessage(),0);
            //
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnErrorNotifyUI(e.getMessage(),0);
                }
            });
        }else{
            this.OnError(e.getMessage(),-1);
            //
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnErrorNotifyUI(e.getMessage(),-1);
                }
            });
        }
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()){
            String response_body_result = response.body().string();
            Log.d(TAG, "onResponse: response_body_result:"+response_body_result);
            final T t = this.OnSuccess(response_body_result,response.code());
            //
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnSuccessNotifyUI(t);
                }
            });
        }else{
            this.OnError(response.message(),response.code());
            //
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnErrorNotifyUI(response.message(),response.code());
                }
            });
        }
    }

    public  abstract T OnSuccess(String result,int statusCode);

    public  abstract void OnSuccessNotifyUI(T t);

    public  abstract void OnError(String errorMsg,int statusCode);

    public  abstract void OnErrorNotifyUI(String errorMsg,int statusCode);


}
