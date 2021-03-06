package com.louis.louisimageupload;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by louisgeek on 2016/7/12.
 */
public class GroupAdapter extends BaseAdapter {
    private List<ImageFloderBean> list;
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    private GridView mGridView;
    protected LayoutInflater mInflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public GroupAdapter(Context context, List<ImageFloderBean> list, GridView mGridView){
        this.list = list;
        this.mGridView = mGridView;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        ImageFloderBean imageFloderBean = list.get(position);
        String path = imageFloderBean.getFirstImagePath();
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_floder_grid, parent,false);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.group_image);
            viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.group_title);
            viewHolder.mTextViewCounts = (TextView) convertView.findViewById(R.id.group_count);

         /*   //用来监听ImageView的宽和高
            viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {

                @Override
                public void onMeasureSize(int width, int height) {
                    mPoint.set(width, height);
                }
            });*/

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
           //## viewHolder.mImageView.setImageResource(R.drawable.friends_sends_pictures_no);
        }

        viewHolder.mTextViewTitle.setText(imageFloderBean.getName());
        viewHolder.mTextViewCounts.setText(Integer.toString(imageFloderBean.getImageCount()));
        //给ImageView设置路径Tag,这是异步加载图片的小技巧
       //#### viewHolder.mImageView.setTag(path);

        Glide.with(parent.getContext())
                .load(imageFloderBean.getFirstImagePath())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.mImageView);
        /*//利用NativeImageLoader类加载本地图片
        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageCallBack() {

            @Override
            public void onImageLoader(Bitmap bitmap, String path) {
                ImageView mImageView = (ImageView) mGridView.findViewWithTag(path);
                if(bitmap != null && mImageView != null){
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });*/

        /*if(bitmap != null){
            viewHolder.mImageView.setImageBitmap(bitmap);
        }else{
            viewHolder.mImageView.setImageResource(R.drawable.friends_sends_pictures_no);
        }*/


        return convertView;
    }



    public static class ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewCounts;
    }


}