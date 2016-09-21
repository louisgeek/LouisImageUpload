package com.louis.louisimageupload;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by louisgeek on 2016/9/20.
 */
public class MyPagerAdapter extends PagerAdapter {

    public MyPagerAdapter(List<View> views) {
        this.views = views;
    }

    List<View> views;

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(views.get(position));
        //### container.removeView((View) object);
    }


}