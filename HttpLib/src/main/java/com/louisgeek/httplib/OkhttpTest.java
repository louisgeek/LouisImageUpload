package com.louisgeek.httplib;/*
package com.louis.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

*/
/**
 * Created by louisgeek on 2016/7/11.
 *//*

public class OkhttpTest {
    //OkHttpClient mOkHttpClient = OkHttpClientSingleton.getInstance().;

//通过同步的方式去操作网络请求，而android本身是不允许在UI线程做网络请求操作的，因此我们需要自己开启一个线程。
        public void doGet() throws Exception {
        Request request = new Request.Builder().url("http:www.baidu.com").build();
        Response response = mOkHttpClient.newCall(request).execute();// execute
        if (response.isSuccessful()) {
            System.out.println(response.code());
            //System.out.println(response.body().string());
            String body=response.body().string();
            System.out.println(body);
           // result="doGet|"+response.code()+"|"+body;
          //  mHandler.sendEmptyMessage(MSG_WHAT);
        }
    }

     void doGetAsync() {
        Request request = new Request.Builder().url("www.baidu.com").build();
        // enqueue
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // NOT UI Thread
                if (response.isSuccessful()) {
                    System.out.println(response.code());
                    //System.out.println(response.body().string());
                    String body=response.body().string();
                    System.out.println(body);
                   // result="doGetAsync|"+response.code()+"|"+body;
                   // mHandler.sendEmptyMessage(MSG_WHAT);
                }
            }
        });
    }

    //通过同步的方式去操作网络请求，而android本身是不允许在UI线程做网络请求操作的，因此我们需要自己开启一个线程。
    private void doPost() throws Exception {
        //表单数据
        RequestBody formBody = new FormBody.Builder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "XXXXXXXXXXXXXXX").build();

        //当写请求头的时候，
        // 使用 header(name, value) 可以设置唯一的name、value。如果已经有值，旧的将被移除，然后添加新的。
        // 使用 addHeader(name, value) 可以添加多值（添加，不移除已有的）。
        Request request = new Request.Builder().url("www.baidu.com")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .post(formBody).build();

        Response response = mOkHttpClient.newCall(request).execute();// execute
        if (response.isSuccessful()) {
            System.out.println(response.code());
            //System.out.println(response.body().string());
            String body=response.body().string();
            System.out.println(body);
           // result="doPost|"+response.code()+"|"+body;
           // mHandler.sendEmptyMessage(MSG_WHAT);

        }
    }

    private void doPostAsync() {
        RequestBody formBody = new FormBody.Builder()
                .add("platform", "android")
                .add("name", "bug")
                .add("subject", "XXXXXXXXXXXXXXX").build();

        Request request = new Request.Builder().url("www.baidu.com")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .post(formBody).build();

        // enqueue
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // NOT UI Thread
                if (response.isSuccessful()) {
                    System.out.println(response.code());

                    //System.out.println(response.body().string());
                    String body=response.body().string();
                    System.out.println(body);
                   // result="doPostAsync|"+response.code()+"|"+body;
                   // mHandler.sendEmptyMessage(MSG_WHAT);
                }

            }

        });

    }
}
*/
