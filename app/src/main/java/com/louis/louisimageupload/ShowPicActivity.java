package com.louis.louisimageupload;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowPicActivity extends AppCompatActivity {

    List<View> views = new ArrayList<>();
    TextView id_tv_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        getSupportActionBar().hide();

        initData();
        ViewPager id_vp = (ViewPager) findViewById(R.id.id_vp);
        id_tv_bottom = (TextView) findViewById(R.id.id_tv_bottom);
        id_tv_bottom.setText(String.valueOf("1/" + views.size()));
        id_vp.setAdapter(new MyPagerAdapter(views));
        id_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                id_tv_bottom.setText(String.valueOf((position + 1) + "/" + views.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    void initData() {
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pic_content_4_vp, null, false);
            ImageView id_iv = (ImageView) view.findViewById(R.id.id_iv);
            id_iv.setImageResource(R.mipmap.ic_launcher);
            views.add(view);
        }
    }

}
