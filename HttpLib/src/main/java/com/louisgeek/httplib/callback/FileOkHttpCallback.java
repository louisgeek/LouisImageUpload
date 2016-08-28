package com.louisgeek.httplib.callback;

import android.os.Environment;
import android.util.Log;

import com.louisgeek.httplib.util.ThreadUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by louisgeek on 2016/7/11.
 */
public abstract class FileOkHttpCallback implements okhttp3.Callback {

    private static final String TAG = "FileOkHttpCallback";
    @Override
    public void onFailure(Call call, final IOException e) {
        //0,  -1   自己定义的。。。
        if (call.isCanceled()){
            this.OnError("FileOkHttpCallback call is canceled,call:"+call.toString()+";message:"+e.getMessage(),0);
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
            //下载完成，保存数据到文件
            InputStream is = response.body().byteStream();
             File tempFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                     + File.separator
                     + System.currentTimeMillis() + ".apK");
             FileOutputStream fos = new FileOutputStream(tempFile);
            byte[] buf = new byte[1024];
            int hasRead = 0;
            while((hasRead = is.read(buf)) > 0) {
                fos.write(buf, 0, hasRead);
            }
            fos.close();
            is.close();

             //
            final String filePath = tempFile.getAbsolutePath();
            Log.d(TAG, "onResponse: filePath:"+filePath);
             this.OnSuccess(filePath,response.code());
            //
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    OnSuccessNotifyUI(filePath,response.code());
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

    public  abstract void OnSuccess(String filePath,int statusCode);

    public  abstract void OnSuccessNotifyUI(String filePath,int statusCode);

    public  abstract void OnError(String errorMsg,int statusCode);

    public  abstract void OnErrorNotifyUI(String errorMsg,int statusCode);
}
