package com.louis.louisimageupload;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImageFloderActivity extends AppCompatActivity {


    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;

    private GridView mGirdView;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private Set<String> mDirPaths=new HashSet();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private  List<ImageFloderBean> mImageFloders= new ArrayList<>();

    private int totalCount= 0;


    //XAM WAY
    private Map<String, List<String>> mGruopMap = new HashMap<>();
    private final static int SCAN_OK = 1;
    private final static int SCAN_OK_TWO = 2;
    private ProgressDialog mProgressDialog;
    private GroupAdapter adapter;
    private GridView mGroupGridView;
    private List<ImageFloderBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_floder);
        mGroupGridView= (GridView) findViewById(R.id.main_grid);
        getImagesTwo();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages(){
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        //新线程
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ImageFloderActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);

                if(mCursor == null){
                    return;
                }
                Log.d("TAG", mCursor.getCount() + "");

                while (mCursor.moveToNext())
                {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    Log.d("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                    {
                        firstImage = path;
                    }
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                    {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloderBean imageFloder;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath))
                    {
                        continue;
                    } else
                    {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloderBean();
                        imageFloder.setDirPath(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    if(parentFile.list()==null){
                        continue;
                    }
                    int picSize = parentFile.list(new FilenameFilter()
                    {
                        @Override
                        public boolean accept(File dir, String filename)
                        {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setImageCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize)
                    {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);

            }
        }).start();

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getImagesTwo() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        //显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ImageFloderActivity.this.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                if(mCursor == null){
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK_TWO);
                mCursor.close();
            }
        }).start();

    }


    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
                    mProgressDialog.dismiss();

                    mImgs = Arrays.asList(mImgDir.list());

                    Log.d("XXXX", "handleMessage: mImgs:"+mImgs.size());
                   /* adapter = new GroupAdapter(ImageFloderActivity.this,mImgs, mGroupGridView);
                    if(list!=null&&list.size()>0) {
                        mGroupGridView.setAdapter(adapter);
                    }*/
                    break;
                case SCAN_OK_TWO:
                    //关闭进度条
                    mProgressDialog.dismiss();

                    adapter = new GroupAdapter(ImageFloderActivity.this, list = subGroupOfImage(mGruopMap), mGroupGridView);
                    if(list!=null&&list.size()>0) {
                        mGroupGridView.setAdapter(adapter);
                        mGroupGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                List<String> childList = mGruopMap.get(list.get(position).getName());

                                Intent mIntent = new Intent(ImageFloderActivity.this, ImageActivity.class);
                                mIntent.putStringArrayListExtra("data", (ArrayList<String>)childList);
                                startActivity(mIntent);

                            }
                        });
                    }
                    break;
            }
        }

    };

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<ImageFloderBean> subGroupOfImage(Map<String, List<String>> mGruopMap){
        if(mGruopMap.size() == 0){
            return null;
        }
        List<ImageFloderBean> list = new ArrayList<>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageFloderBean imageFloderBean = new ImageFloderBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            imageFloderBean.setName(key);
            imageFloderBean.setImageCount(value.size());
            imageFloderBean.setFirstImagePath(value.get(0));//获取该组的第一张图片

            list.add(imageFloderBean);
        }

        return list;

    }
}
