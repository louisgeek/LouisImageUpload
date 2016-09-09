package com.louisgeek.httplib;

import android.os.Environment;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.louisgeek.httplib.bean.BaseBean;
import com.louisgeek.httplib.bean.BaseListBean;
import com.louisgeek.httplib.bean.OthersBean;
import com.louisgeek.httplib.callback.GsonOkHttpCallback;
import com.louisgeek.httplib.callback.SimpleGsonOkHttpCallback;
import com.louisgeek.httplib.callback.SimpleStringOkHttpCallback;
import com.louisgeek.httplib.callback.StringOkHttpCallback;
import com.louisgeek.httplib.util.MD5Util;
import com.louisgeek.httplib.util.ParamsUtil;
import com.louisgeek.httplib.util.ThreadUtil;
import com.louisgeek.httplib.util.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by louisgeek on 2016/7/11.
 *
 *  OKHTTP 3 使用 DEMO
 */
public class OkHttpClientSingleton {
    private static final String TAG = "OkHttpClientSingleton";

    /**
     * "application/x-www-form-urlencoded"，他是默认的MIME内容编码类型，一般可以用于所有的情况。但是他在传输比较大的二进制或者文本数据时效率极低。
     * 这种情况应该使用"multipart/form-data"。如上传文件或者二进制数据和非ASCII数据。
     */
    public static final MediaType MEDIA_TYPE_NORAML_FORM=MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //既可以提交普通键值对，也可以提交(多个)文件键值对。
    public static final MediaType MEDIA_TYPE_MULTIPART_FORM=MediaType.parse("multipart/form-data; charset=utf-8");
    //只能提交二进制，而且只能提交一个二进制，如果提交文件的话，只能提交一个文件,后台接收参数只能有一个，而且只能是流（或者字节数组）
    public static final MediaType MEDIA_TYPE_STREAM=MediaType.parse("application/octet-stream");
    public static final MediaType MEDIA_TYPE_TEXT=MediaType.parse("text/plain; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON=MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG=MediaType.parse("image/png");

    private  OkHttpClient mOkHttpClient;
   /*
    *//* 私有构造方法，防止被外部实例化 *//*
    private OkHttpClientSingleton() {
        mOkHttpClient=new OkHttpClient();
        mOkHttpClient.newBuilder().connectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().readTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().writeTimeout(30, TimeUnit.SECONDS);
    }*/
    /* 私有构造方法，防止被外部实例化 */
   private OkHttpClientSingleton() {
       mOkHttpClient = new OkHttpClient.Builder()
               .connectTimeout(30,TimeUnit.SECONDS)
               .readTimeout(30,TimeUnit.SECONDS)
               .writeTimeout(30,TimeUnit.SECONDS)
               .build();
   }

    static{
      /*
       OkHTTP 3  不能用  后期赋值不了 也添加不了拦截器
       mOkHttpClient.newBuilder().connectTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().readTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().writeTimeout(30, TimeUnit.SECONDS);*/
    }


    private static class SingletonInstance {
        private static final OkHttpClientSingleton INSTANCE = new OkHttpClientSingleton();
    }

    public static OkHttpClientSingleton getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @Deprecated
    public void doGetANew() {
        final Request request = new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther?Version=2&id=1")
                .headers((Headers.of(setupDefaultHeaders())))
                .build();
           GsonOkHttpCallback<BaseListBean<OthersBean>> okHttpCallback=new GsonOkHttpCallback<BaseListBean<OthersBean>>() {
            @Override
            public BaseListBean<OthersBean> OnSuccess(String result, int statusCode) {
                return BaseListBean.fromJsonThree(result);
            }

            @Override
            public void OnSuccessNotifyUI(BaseListBean<OthersBean> othersBeanBaseListBean) {
                Log.d(TAG, "OnSuccessNotifyUI: othersBeanBaseListBean:"+othersBeanBaseListBean.toString());
            }

            @Override
            public void OnError(String errorMsg, int statusCode) {
                Log.d(TAG, "OnError: errorMsg:"+errorMsg);
            }

            @Override
            public void OnErrorNotifyUI(String errorMsg, int statusCode) {
                Log.d(TAG, "OnErrorNotifyUI: errorMsg:"+errorMsg);
            }
        };
        mOkHttpClient.newCall(request).enqueue(okHttpCallback);

    }
    @Deprecated
    public void doGetANew2() {
        final Request request = new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther?Version=2&id=1")
                .headers((Headers.of(setupDefaultHeaders())))
                .build();

            SimpleGsonOkHttpCallback<BaseListBean<OthersBean>> simpleGsonOkHttpCallback=new SimpleGsonOkHttpCallback<BaseListBean<OthersBean>>() {
            @Override
            public BaseListBean<OthersBean> OnSuccess(String result, int statusCode) {
                return BaseListBean.fromJsonThree(result);
            }

            @Override
            public void OnSuccessNotifyUI(BaseListBean<OthersBean> othersBeanBaseListBean) {
                super.OnSuccessNotifyUI(othersBeanBaseListBean);
                Log.d(TAG, "OnSuccessNotifyUI out: othersBeanBaseListBean:"+othersBeanBaseListBean);
            }
        };
        mOkHttpClient.newCall(request).enqueue(simpleGsonOkHttpCallback);

    }
    @Deprecated
    public void doGetANew3() {
        final Request request = new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther?Version=2&id=1")
                .headers((Headers.of(setupDefaultHeaders())))
                .build();

         StringOkHttpCallback stringOkHttpCallback=new StringOkHttpCallback() {
         @Override
         public void OnSuccess(String result, int statusCode) {
             Log.d(TAG, "OnSuccess: result:"+result);
         }

         @Override
         public void OnSuccessNotifyUI(String result, int statusCode) {
             Log.d(TAG, "OnSuccess: result:"+result);
         }

         @Override
         public void OnError(String errorMsg, int statusCode) {
             Log.d(TAG, "OnError: errorMsg:"+errorMsg);
         }

         @Override
         public void OnErrorNotifyUI(String errorMsg, int statusCode) {
             Log.d(TAG, "OnErrorNotifyUI: errorMsg:"+errorMsg);
         }
     };
        mOkHttpClient.newCall(request).enqueue(stringOkHttpCallback);

    }
    @Deprecated
    public void doGetANew4() {
        final Request request = new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther?Version=2&id=1")
                .headers((Headers.of(setupDefaultHeaders())))
                .build();

      SimpleStringOkHttpCallback simpleStringOkHttpCallback=new SimpleStringOkHttpCallback() {
          @Override
          public void OnSuccess(String result, int statusCode) {
              Log.d(TAG, "OnSuccess: result:"+result);
          }

          @Override
          public void OnSuccessNotifyUI(String result, int statusCode) {
              super.OnSuccessNotifyUI(result, statusCode);
              Log.d(TAG, "OnSuccessNotifyUI out: result:"+result);
          }
      };
        mOkHttpClient.newCall(request).enqueue(simpleStringOkHttpCallback);

    }
    @Deprecated
    public void doGetA(){
        final Request request=new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther?Version=2&id=1")
                .headers((Headers.of(setupDefaultHeaders())))
                .build();

        Callback callback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "okhttp onFailure: e:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // Log.d(TAG, "okhttp onResponse: body:"+response.body().string());
                if (response.isSuccessful()) {
                    System.out.println(response.code());

                    String body=response.body().string();
                    System.out.println(body);

                    /**
                     *
                     D/OkHttpClientSingleton: okhttp onResponse:baseListBean1 BaseBean{code=0, message='获取成功！', info=[{recordtime=null, inserttime=2016-05-31 16:13:11.0, modifytime=1900-01-01 00:00:00.0, otherimageslocation=http://manage.lvseeds.com//uploadfile/image/20160531/20165311613042156450.JPG|http://manage.lvseeds.com//uploadfile/image/20160531/201653116130991206774.jpg, recordtype=null, plantid=1, id=1, otherimages=177,178, checkstate=1}]}
                     D/OkHttpClientSingleton: okhttp onResponse:baseListBean2 BaseBean{code=0, message='获取成功！', info=[{recordtime=null, inserttime=2016-05-31 16:13:11.0, modifytime=1900-01-01 00:00:00.0, otherimageslocation=http://manage.lvseeds.com//uploadfile/image/20160531/20165311613042156450.JPG|http://manage.lvseeds.com//uploadfile/image/20160531/201653116130991206774.jpg, recordtype=null, plantid=1, id=1, otherimages=177,178, checkstate=1}]}
                     D/OkHttpClientSingleton: okhttp onResponse:baseListBean3 BaseBean{code=0, message='获取成功！', info=[OthersListBean{recordtime='null', inserttime='2016-05-31 16:13:11.0', modifytime='1900-01-01 00:00:00.0', otherimageslocation='http://manage.lvseeds.com//uploadfile/image/20160531/20165311613042156450.JPG|http://manage.lvseeds.com//uploadfile/image/20160531/201653116130991206774.jpg', recordtype='null', plantid='1', id='1', otherimages='177,178', checkstate='1'}]}
                     D/OkHttpClientSingleton: okhttp onResponse:baseListBean4 BaseBean{code=0, message='获取成功！', info=[{recordtime=null, inserttime=2016-05-31 16:13:11.0, modifytime=1900-01-01 00:00:00.0, otherimageslocation=http://manage.lvseeds.com//uploadfile/image/20160531/20165311613042156450.JPG|http://manage.lvseeds.com//uploadfile/image/20160531/201653116130991206774.jpg, recordtype=null, plantid=1, id=1, otherimages=177,178, checkstate=1}]}
                     D/OkHttpClientSingleton: okhttp onResponse:baseListBean5 BaseBean{code=0, message='获取成功！', info=[OthersListBean{recordtime='null', inserttime='2016-05-31 16:13:11.0', modifytime='1900-01-01 00:00:00.0', otherimageslocation='http://manage.lvseeds.com//uploadfile/image/20160531/20165311613042156450.JPG|http://manage.lvseeds.com//uploadfile/image/20160531/201653116130991206774.jpg', recordtype='null', plantid='1', id='1', otherimages='177,178', checkstate='1'}]}
                     */
                    /**
                     * 肯定不能用
                     */
                    BaseListBean<OthersBean> baseListBean1=BaseListBean.fromJsonOne(body);
                    Log.d(TAG, "okhttp onResponse:baseListBean1 "+baseListBean1.toString());
                    //会报 java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.louis.okhttp.bean.OthersBean
                   //### Log.d(TAG, "okhttp onResponse:baseListBean1 getPlantid"+baseListBean1.getInfo().get(0).getPlantid());

                    /**
                     * 不能用
                     */
                    BaseListBean<OthersBean> baseListBean2=new BaseListBean<>();
                    baseListBean2=baseListBean2.fromJsonTwo(body);
                    Log.d(TAG, "okhttp onResponse:baseListBean2 "+baseListBean2.toString());
                    //会报 java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.louis.okhttp.bean.OthersBean
                    //###Log.d(TAG, "okhttp onResponse:baseListBean2 getPlantid"+baseListBean2.getInfo().get(0).getPlantid());
                    /**
                     * 不推荐用 内部写死  不灵活
                     */
                    BaseListBean<OthersBean> baseListBean3=BaseListBean.fromJsonThree(body);
                    Log.d(TAG, "okhttp onResponse:baseListBean3 "+baseListBean3.toString());
                    Log.d(TAG, "okhttp onResponse:baseListBean3 getPlantid"+baseListBean3.getInfo().get(0).getPlantid());

                    /**
                     * 不能用
                     */
                    BaseListBean<OthersBean> baseListBean4=BaseListBean.fromJsonFour(body,OthersBean.class);
                    Log.d(TAG, "okhttp onResponse:baseListBean4 "+baseListBean4.toString());
                    //会报 java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.louis.okhttp.bean.OthersBean
                    //###Log.d(TAG, "okhttp onResponse:baseListBean4 getPlantid"+baseListBean4.getInfo().get(0).getPlantid());

                    /**
                     * 可用 推荐方式  类似于gson.fromJson(jsonString, new TypeToken<List<String>>(){}.getType());
                     * 好理解
                     */
                    TypeToken<BaseListBean<OthersBean>> typeToken=new TypeToken<BaseListBean<OthersBean>>(){};
                    BaseListBean<OthersBean> baseListBean5=BaseListBean.fromJsonFive(body,typeToken);
                    Log.d(TAG, "okhttp onResponse:baseListBean5 "+baseListBean5.toString());
                    Log.d(TAG, "okhttp onResponse:baseListBean5 getPlantid"+baseListBean5.getInfo().get(0).getPlantid());

                    /**
                     * 可用
                     */
                    BaseListBean<OthersBean> baseListBean6=BaseListBean.fromJsonSix(body,OthersBean.class);
                    Log.d(TAG, "okhttp onResponse:baseListBean6 "+baseListBean6.toString());
                    Log.d(TAG, "okhttp onResponse:baseListBean6 getPlantid"+baseListBean6.getInfo().get(0).getPlantid());


                    String jsonStr_info_is_obj="{\"code\": 0,\"message\": \"获取成功！\",\"info\": {\"recordtime\":\"null\",\"inserttime\":\"2016-05-31 16:13:11.0\",\"modifytime\":\"1900-01-01 00:00:00.0\",\"otherimageslocation\":\"http://manage.lvseeds.com//uploadfile/image/20160531/20165311613042156450.JPG|http://manage.lvseeds.com//uploadfile/image/20160531/201653116130991206774.jpg\",\"recordtype\":\"null\",\"plantid\":\"1\",\"id\":\"1\",\"otherimages\":\"177,178\",\"checkstate\":\"1\"}}";

                    TypeToken<BaseBean<OthersBean>> typeToken2=new TypeToken<BaseBean<OthersBean>>(){};
                    BaseBean<OthersBean> baseBean= BaseBean.fromJsonOne(jsonStr_info_is_obj,typeToken2);
                    Log.d(TAG, "okhttp onResponse:baseBean "+baseBean.toString());
                    Log.d(TAG, "okhttp onResponse:baseBean getPlantid"+baseBean.getInfo().getPlantid());

                    BaseBean<OthersBean> baseBean2=new BaseBean<>();
                    baseBean2=baseBean2.fromJsonThree(jsonStr_info_is_obj);
                    Log.d(TAG, "okhttp onResponse:baseBean2 "+baseBean2.toString());
                    //会报 java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.louis.okhttp.bean.OthersBean
                    //###Log.d(TAG, "okhttp onResponse:baseBean2 getPlantid"+baseBean2.getInfo().getPlantid());

                    BaseBean<OthersBean> baseBean3=BaseBean.fromJsonFour(jsonStr_info_is_obj,OthersBean.class);
                    Log.d(TAG, "okhttp onResponse:baseBean3 "+baseBean3.toString());
                    //会报 java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.louis.okhttp.bean.OthersBean
                    //###Log.d(TAG, "okhttp onResponse:baseBean3 getPlantid"+baseBean3.getInfo().getPlantid());


                }

            }
        };

        mOkHttpClient.newCall(request).enqueue(callback);

    }


    public final String UserId = "A6971118873561";
    public final String UserPassword = UserId + "UZ" + "8C757B31-A896-F477-C46D-4E27E05528D3" + "UZ";

    public Map<String, String> setupDefaultHeaders()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());

        Map<String,String> map = new HashMap<>();
        map.put("UserId", UserId);
        map.put("UserPassword", MD5Util.encode(UserPassword+time));
        map.put("CurrentTime", time);
        return map;
    }
    @Deprecated
    public void doPostA(){
        //表单数据
        RequestBody formBody = new FormBody.Builder()
                .add("Version", "2")
                .add("ID", "1")
                .build();

        Request request=new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther")
                .headers((Headers.of(setupDefaultHeaders())))
                .post(formBody)
                .build();


        Callback callback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "okhttp onFailure: e:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "okhttp onResponse: body:"+response.body().string());
            }
        };

        mOkHttpClient.newCall(request).enqueue(callback);

    }

    /**
     * GET   测试通过
     */
    public  void doGetAsync(String url,Callback responseCallback){
        doGetAsync(url,null,"",responseCallback);
    }
    /**
     * GET   测试通过
     */
    public  void doGetAsync(String url,String queryStr,Callback responseCallback){
        doGetAsync(url,null,queryStr,responseCallback);
    }
    /**
     * GET   测试通过
     */
    public  void doGetAsync(String url,Map<String, String> queryStrMap,Callback responseCallback){
        doGetAsync(url,null, UrlUtil.mapToQueryStr(queryStrMap),responseCallback);
    }
    /**
     * GET   测试通过
     */
    public  void doGetAsync(String url,Map<String, String> headersMap,Map<String, String> queryStrMap,Callback responseCallback){
        doGetAsync(url,headersMap,UrlUtil.mapToQueryStr(queryStrMap),responseCallback);
    }
    /**
     * GET   测试通过
     */
    public  void doGetAsync(String url,Map<String, String> headersMap,String queryStr,Callback responseCallback){
        if (queryStr!=null&&!queryStr.equals("")&&url!=null) {
            url=UrlUtil.dealUrlAndQueryStr(url,queryStr);
        }
      /*  Request request=new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther")
                .headers((Headers.of(getHeaders())))
                .post(formBody)
                .build();*/
        //
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        //默认需要加的验证
        requestBuilder.headers((Headers.of(setupDefaultHeaders())));
        if (headersMap!=null) {
            for(Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        request=requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * POST   测试通过
     */
    public  void doPostAsync(String url,Callback responseCallback){
        doPostAsync(url,null,"",responseCallback);
    }
    /**
     * POST   测试通过
     */
    public  void doPostAsync(String url,Map<String, String> paramsMap,Callback responseCallback){
        doPostAsync(url,null,paramsMap,responseCallback);
    }
    /**
     * POST   测试通过
     */
    public  void doPostAsync(String url,String paramsMapStr,Callback responseCallback){
        doPostAsync(url,null, ParamsUtil.paramsStrToMap(paramsMapStr),responseCallback);
    }
    /**
     * POST   测试通过
     */
    public  void doPostAsync(String url,Map<String, String> headersMap,String paramsMapStr,Callback responseCallback){
        doPostAsync(url,headersMap, ParamsUtil.paramsStrToMap(paramsMapStr),responseCallback);
    }
    /**
     * POST   测试通过
     */
    public  void doPostAsync(String url,Map<String, String> headersMap,Map<String, String> paramsMap,Callback responseCallback){
        Log.d(TAG, "doPostAsync: url:"+url);
        //表单数据
       /* RequestBody formBody = new FormBody.Builder()
                .add("ID", "1")
                .add("ID2", "2")
                .build();*/
        FormBody formBody= null;
        if (paramsMap!=null) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                // System.out.println("key= "+ key + " and value= " + paramsMap.get(key));
                String value = paramsMap.get(key);
                formBodyBuilder.add(key, value);
            }
            formBody = formBodyBuilder.build();
        }
      /*  Request request=new Request.Builder()
                .url("http://api.lvseeds.com:8080/lvseeds/Melonplant/GetOther")
                .headers((Headers.of(getHeaders())))
                .post(formBody)
                .build();*/
        //
        Request request;
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        //默认需要加的验证
        requestBuilder.headers((Headers.of(setupDefaultHeaders())));
        if (headersMap!=null) {
            for(Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(),entry.getValue());
            }
        }
        if (formBody!=null) {
            requestBuilder.post(formBody);
        }
        request=requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    public  void doPostAsyncMulti(Callback responseCallback){
       // String path1=Environment.getExternalStorageDirectory()+"222.txt";
        String path2=Environment.getExternalStorageDirectory()+"/111.png";
       // File file1 = new File(path1);
        File file2image = new File(path2);
      //  RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file1);
        RequestBody imageFileBody = RequestBody.create(MEDIA_TYPE_PNG, file2image);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
               // .addFormDataPart("files", null,fileBody)
                .addFormDataPart("uploadimg", null,imageFileBody)
                .addFormDataPart("id", "dd")//
                .addFormDataPart("name", "sdd")
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1:8090/ImageUpDownLoad/SmartUploadServlet")
                .post(multipartBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }
    /**
     * 上传  String   测试通过
     * @param url
     * @param strContent
     * @param responseCallback
     */
    public  void doPostAsyncString(String url,String strContent,MediaType mediaType,Callback responseCallback){
        RequestBody requestBody = RequestBody.create(mediaType,strContent);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(setupDefaultHeaders()))
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 上传 JSON String  测试通过
     * @param url
     * @param strContent
     * @param responseCallback
     */
    public  void doPostAsyncJson(String url,String strContent,Callback responseCallback){
        doPostAsyncString(url,strContent,MEDIA_TYPE_JSON,responseCallback);
    }

    public  void doPostAsyncStream(String url,Callback responseCallback){

        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_STREAM;
            }
            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }
            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    public  void doPostAsyncFile(String url,Callback responseCallback){
        String path=Environment.getExternalStorageDirectory()+"/123.txt";
        File file = new File(path);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_TEXT, file))
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    public  void doPostAsyncFileWithProgress_Multi(String url,Callback responseCallback){
        String path=Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "123.txt";
        File file = new File(path);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("files",null, RequestBody.create(MEDIA_TYPE_TEXT, file))
                .build();

        UploadProgressRequestBody progressRequestBody = new UploadProgressRequestBody(requestBody, new OnUploadProgressListener() {
            @Override
            public void onUploadProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (100.0 * bytesRead / contentLength);
                Log.d(TAG, "onUploadProgress: progress:"+progress);
            }
        });
        Request request = new Request.Builder()
                .url(url)
                .post(progressRequestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }

    /**
     * 下载文件   测试通过
     * @param url
     */
    public  void doGetAsyncFile(String url,Callback responseCallback){
      int  connectTimeoutMillis= mOkHttpClient.connectTimeoutMillis();

        Log.d(TAG, "connectTimeoutMillis:"+connectTimeoutMillis);

        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }
    /**
     * 下载文件带进度   测试通过  进度OK
     * @param url
     */
    public  void doGetAsyncFileWithProgress(String url,final OnDownloadProgressListener onDownloadProgressListener,Callback responseCallback){

      /*  final OnDownloadProgressListener onDownloadProgressListener=new OnDownloadProgressListener() {
            @Override
            public void onDownloadloadProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (100.0 * bytesRead / contentLength);
                Log.d(TAG, "onDownloadloadProgress: progress:"+progress);
            }
        };*/
        //增加拦截器  okhttp 3 注意  不清楚可以直接参照http://www.it1352.com/138888.html 创建一个OkHttpClient
        OkHttpClient.Builder okHttpClientBuilder = mOkHttpClient.newBuilder();
        okHttpClientBuilder.addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                /*Request request = chain.request().newBuilder()
                                        .addHeader("Accept", "Application/JSON").build();
                                return chain.proceed(request);*/
                                //拦截
                                Response originalResponse = chain.proceed(chain.request());
                                //包装响应体并返回
                                return originalResponse.newBuilder()
                                        .body(new DownloadProgressResponseBody(originalResponse.body(),onDownloadProgressListener))
                                        .build();
                            }
                        }).build();

      /* okhttp 3.0不行  okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());
                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(new DownloadProgressResponseBody(originalResponse.body(),onDownloadProgressListener))
                        .build();
            }
        });*/

        mOkHttpClient=okHttpClientBuilder.build();
        Log.d(TAG, "mOkHttpClient: connectTimeoutMillis:"+mOkHttpClient.connectTimeoutMillis());
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    //上传的  自定义的RequestBody，能够显示进度
    private static class UploadProgressRequestBody extends RequestBody {
        //实际的待包装请求体
        private final RequestBody requestBody;
        //进度回调接口
        private final OnUploadProgressListener onUploadProgressListener;
        //包装完成的BufferedSink
        private BufferedSink bufferedSink;

        /**
         * 构造函数，赋值
         * @param requestBody      待包装的请求体
         * @param onUploadProgressListener 回调接口
         */
        public UploadProgressRequestBody(RequestBody requestBody, OnUploadProgressListener onUploadProgressListener) {
            this.requestBody = requestBody;
            this.onUploadProgressListener = onUploadProgressListener;
        }
        /**
         * 重写调用实际的响应体的contentType
         * @return MediaType
         */
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }
        /**
         * 重写调用实际的响应体的contentLength
         * @return contentLength
         * @throws IOException 异常
         */
        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }
        /**
         * 重写进行写入
         * @param sink BufferedSink
         * @throws IOException 异常
         */
        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                //包装
                bufferedSink = Okio.buffer(sink(sink));
            }
            //写入
            requestBody.writeTo(bufferedSink);
            //必须调用flush，否则最后一部分数据可能不会被写入
            bufferedSink.flush();
        }
        /**
         * 写入，回调进度接口
         * @param sink Sink
         * @return Sink
         */
        private Sink sink(Sink sink) {
            return new ForwardingSink(sink) {
                //当前写入字节数
                long bytesWritten = 0L;
                //总字节长度，避免多次调用contentLength()方法
                long contentLength = 0L;
                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0) {
                        //获得contentLength的值，后续不再调用
                        contentLength = contentLength();
                    }
                    //增加当前写入的字节数
                    bytesWritten += byteCount;
                    //回调
                    onUploadProgressListener.onUploadProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                }
            };
        }
    }

    //下载的  自定义的ResponseBody，在其中处理进度
    private static class DownloadProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final OnDownloadProgressListener onDownloadProgressListener;
        private BufferedSource bufferedSource;

        public DownloadProgressResponseBody(ResponseBody responseBody, OnDownloadProgressListener onDownloadProgressListener) {
            this.responseBody = responseBody;
            this.onDownloadProgressListener = onDownloadProgressListener;
        }

        @Override public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override public long contentLength() {
            return responseBody.contentLength();
        }

        @Override public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;
                int progress;
                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    long  contentLength=responseBody.contentLength();//不要调用多次  好像会出错
                    onDownloadProgressListener.onDownloadloadProgress(totalBytesRead, contentLength, bytesRead == -1);

                    progress = (int) (totalBytesRead*100.0/contentLength);
                    //Log.d(TAG, "read: progress:"+progress);
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onDownloadProgressListener.onDownloadloadProgressNotifyUI(progress);
                        }
                    });

                    return bytesRead;
                }
            };
        }
    }


    //上传进度回调接口
   public interface OnUploadProgressListener {
        void onUploadProgress(long bytesRead, long contentLength, boolean done);
        //void onUploadProgressNotifyUI(int progress);
    }

    //下载进度回调接口
    public interface OnDownloadProgressListener {
        void onDownloadloadProgress(long bytesRead, long contentLength, boolean done);
        void onDownloadloadProgressNotifyUI(int progress);
    }
}
