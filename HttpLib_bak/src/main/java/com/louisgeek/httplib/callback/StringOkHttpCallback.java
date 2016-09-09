package com.louisgeek.httplib.callback;

import android.util.Log;

import com.louisgeek.httplib.util.ThreadUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by louisgeek on 2016/7/11.
 */
public abstract class StringOkHttpCallback  implements okhttp3.Callback {

    private static final String TAG = "StringOkHttpCallback";
    @Override
    public void onFailure(Call call, final IOException e) {
        //0,  -1   自己定义的。。。
        if (call.isCanceled()){
            this.OnError("StringOkHttpCallback call is canceled,call:"+call.toString()+";message:"+e.getMessage(),0);
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
            final String response_body_result = response.body().string();
            Log.d(TAG, "onResponse: response_body_result:"+response_body_result);
             this.OnSuccess(response_body_result,response.code());
            //
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnSuccessNotifyUI(response_body_result,response.code());
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

    public  abstract void OnSuccess(String result,int statusCode);

    public  abstract void OnSuccessNotifyUI(String result,int statusCode);

    public  abstract void OnError(String errorMsg,int statusCode);

    public  abstract void OnErrorNotifyUI(String errorMsg,int statusCode);
}
