package com.example.xiaoniu.publicuseproject.glide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.xiaoniu.publicuseproject.R;


public class GlideActivity extends Activity {

    private GridView gridview;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();

    }
    private void initView() {
        gridview = (GridView) findViewById(R.id.gridview);
        adapter = new PhotoAdapter(this, Images.imageThumbUrls);
        gridview.setAdapter(adapter);

    }
}
