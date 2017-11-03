package com.example.xiaoniu.publicuseproject.LruCache;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.example.xiaoniu.publicuseproject.R;

public class PhotoWallActivity extends Activity {

//    /**
//     * 用于展示照片墙的GridView
//     */
//    private GridView mPhotoWall;
//
//    /**
//     * GridView的适配器
//     */
//    private PhotoWallAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.photo_wall_layout);
//        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
//        adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
//        mPhotoWall.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // 退出程序时结束所有的下载任务
//        adapter.cancelAllTasks();
//    }

    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallDiskAdapter mAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_wall_layout);
        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing);
        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        mAdapter = new PhotoWallDiskAdapter(this, 0, Images.imageThumbUrls,
                mPhotoWall);
        mPhotoWall.setAdapter(mAdapter);
        mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(mPhotoWall
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (mPhotoWall.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            mAdapter.setItemHeight(columnWidth);
                            mPhotoWall.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        mAdapter.cancelAllTasks();
    }
}
