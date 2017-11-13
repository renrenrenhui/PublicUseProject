package com.example.xiaoniu.publicuseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xiaoniu.publicuseproject.R;


public class Fragment4 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView mImageView;

    public Fragment4(){}

    @Override
    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    @Override
    public int initLayout() {
        return R.layout.fragment4;
    }

    @Override
    public void initViews(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.sub_color_1, R.color.sub_color_2, R.color.sub_color_3, R.color.sub_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mImageView = (ImageView) view.findViewById(R.id.image_view);
    }

    @Override
    public void initResource() {
        Glide.with(getActivity()).load("http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg").listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                mSwipeRefreshLayout.setRefreshing(false);
                setNetUnable(false);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                mSwipeRefreshLayout.setRefreshing(false);
                return false;
            }
        }).into(mImageView);
    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void onRefresh() {
    }

    @Override
    protected void onFragmentFirstVisible() {
        if (!isNetWorkActive(getContext())) {
            mSwipeRefreshLayout.setRefreshing(false);
            setNetUnable(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
