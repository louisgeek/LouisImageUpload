package com.louis.louisimageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.google.gson.Gson;
import com.louisgeek.httplib.OkHttpClientSingleton;
import com.louisgeek.httplib.bean.UploadBase64ImgsBean;
import com.louisgeek.httplib.callback.SimpleFileOkHttpCallback;
import com.louisgeek.httplib.callback.SimpleStringOkHttpCallback;
import com.louisgeek.httplib.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    List<ImageBean> imageBeanList;
    private Button idClick;
    MyDialogFragmentProgress myDialogFragmentProgress=MyDialogFragmentProgress.newInstance("XXX");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  ProgressDialog progressDialog=ProgressDialog.show(this,"XXX","yyy");

        idClick = (Button) findViewById(R.id.id_click);
        idClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OkHttpClientSingleton.getInstance().doGetANew();
                //OkHttpClientSingleton.getInstance().doGetANew2();
                //OkHttpClientSingleton.getInstance().doGetANew3();
                //OkHttpClientSingleton.getInstance().doGetANew4();
               /* Map<String,String> map=new HashMap<>();
                map.put("citykey","101010100");*/
                // OkHttpClientSingleton.doGetAsync("http://wthrcdn.etouch.cn/weather_mini",map,simpleStringOkHttpCallback);
                // OkHttpClientSingleton.doGetAsync("http://wthrcdn.etouch.cn/weather_mini","citykey=101010100",simpleStringOkHttpCallback);
                //OkHttpClientSingleton.doGetAsync("http://wthrcdn.etouch.cn/weather_mini",simpleStringOkHttpCallback);
                //OkHttpClientSingleton.getInstance().doGetAsyncFile("http://count.liqucn.com/d.php?id=10737&urlos=android&from_type=web");
                myDialogFragmentProgress.show(getSupportFragmentManager(),"myDialogFragmentProgress");
                myDialogFragmentProgress.setCancelable(false);

                SimpleFileOkHttpCallback simpleFileOkHttpCallback=new SimpleFileOkHttpCallback() {
                    @Override
                    public void OnSuccess(String filePath, int statusCode) {
                        Log.d(TAG, "OnSuccess: filePath:"+filePath);
                    }
                };
                OkHttpClientSingleton.OnDownloadProgressListener onDownloadProgressListener=new OkHttpClientSingleton.OnDownloadProgressListener() {
                    @Override
                    public void onDownloadloadProgress(long bytesRead, long contentLength, boolean done) {
                        final int progress = (int) (100.0 * bytesRead / contentLength);
                        Log.d(TAG, "onDownloadloadProgress: progress:"+progress);
                    }

                    @Override
                    public void onDownloadloadProgressNotifyUI(int progress) {
                        Log.d(TAG, "onDownloadloadProgressNotifyUI: progress:"+progress);
                        myDialogFragmentProgress.updateProgress(progress);
                        if (progress==100){
                            myDialogFragmentProgress.dismiss();
                        }
                    }
                };

                OkHttpClientSingleton.getInstance()
                        .doGetAsyncFileWithProgress("http://count.liqucn.com/d.php?id=10737&urlos=android&from_type=web",
                                onDownloadProgressListener,
                                simpleFileOkHttpCallback);

            }
        });
        Button id_click_post = (Button) findViewById(R.id.id_click_post);
        id_click_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String path= Environment.getExternalStorageDirectory()+"/111.png";
                String dataJson= "{\"base64ImgsJsonStr\":"+getBase64ImgsJsonStr(path)+"}";

                SimpleStringOkHttpCallback simpleStringOkHttpCallback=new SimpleStringOkHttpCallback() {
                    @Override
                    public void OnSuccess(String result, int statusCode) {
                        Log.d(TAG, "OnSuccess: result:"+result);
                    }
                };

                OkHttpClientSingleton.getInstance()
                        .doPostAsyncJson("http://192.168.1.67:8002/Attachment/NoWeatherAddImagesApp?Version=2&id=82",dataJson,simpleStringOkHttpCallback);
               // OkHttpClientSingleton.doPostAsync("http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi","chgmobile=15850781443",simpleStringOkHttpCallback);
            }
        });


        GridView gridView = (GridView) findViewById(R.id.id_gv);


        imageBeanList=new ArrayList<>();

        for (int i = 0; i <5 ; i++) {
            ImageBean imageBean=new ImageBean();
            imageBeanList.add(imageBean);
        }

        MyGridAdapter myGridAdapter=new MyGridAdapter(imageBeanList);
        gridView.setAdapter(myGridAdapter);
        myGridAdapter.setOnAddClickListener(new MyGridAdapter.OnAddClickListener() {
            @Override
            public void onAddClick(View view) {
                Intent intent=new Intent(MainActivity.this,ImageFloderActivity.class);
                startActivity(intent);
            }
        });
    }


    private String getBase64ImgsJsonStr(String filePath) {

        int nowUserID=Integer.valueOf(String.valueOf(82));

        File file=new File(filePath);
        String fileName=file.getName();
        Bitmap bitmap=null;
        if (filePath !=null && filePath.length() > 0) {
            bitmap = ImageUtil.zoomBitmapFromFileWithWidthHeight(file, 400, 400);
        }
        byte[] imgBytes = ImageUtil.Bitmap2Bytes(bitmap);
        String Base64Str= Base64.encodeToString(imgBytes, Base64.DEFAULT);

        List<UploadBase64ImgsBean> list=new ArrayList<>();

        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println(prefix);
        //用String的endsWith(".java")方法判断是否问指定文件类型。
        String prefixHasDot="."+prefix;
        UploadBase64ImgsBean imgs1=new UploadBase64ImgsBean(nowUserID, fileName,"2016-7-12 16:00:46", prefixHasDot, Base64Str);
        // UploadImgs imgs2=new UploadImgs(NowUserID, "120.jpg", ".jpg", Base64Str);
        list.add(imgs1);
        // list.add(imgs2);
        Gson gson=new Gson();
        String base64ImgsJsonStr= gson.toJson(list);
        return base64ImgsJsonStr;

    }
}
