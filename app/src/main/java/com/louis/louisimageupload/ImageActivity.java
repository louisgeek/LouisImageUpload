package com.louis.louisimageupload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mGridView = (GridView) findViewById(R.id.child_grid);
        list = getIntent().getStringArrayListExtra("data");

        adapter = new ChildAdapter(this, list, mGridView);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ImageActivity.this,ShowPicActivity.class);
                startActivity(intent);
            }
        });
        mGridView.setAdapter(adapter);
    }
}
