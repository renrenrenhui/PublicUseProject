package com.example.xiaoniu.publicuseproject.dial;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;


/**
 * Created by panwenjuan on 17-8-9.
 */
public class FloatWindowHideView extends RelativeLayout {

    private Context mContext;

    private TextView mHideSuspendTv;
    private ImageView mHideIv;
    private RelativeLayout mHideSuspendRl;

    public FloatWindowHideView(Context context) {
        super(context);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.float_view_hide, this);
        mHideSuspendRl = (RelativeLayout) findViewById(R.id.rl_hide);
        mHideSuspendTv = (TextView) findViewById(R.id.tv_hide);
        mHideIv = (ImageView) findViewById(R.id.iv_hide);

        updateHideView(false);
    }

    public void updateHideView(boolean isCollsionWithRect) {
        Drawable drawable = null;
        if (isCollsionWithRect) {
            drawable = getResources().getDrawable(R.drawable.ic_close);
        } else {
            drawable = getResources().getDrawable(R.drawable.ic_close);
        }
        if (null != drawable) {
            drawable.setBounds(0, 0, ScreenUtils.dip2px(mContext, 56), ScreenUtils.dip2px(mContext, 56));
        }

        mHideIv.setImageDrawable(drawable);
        mHideSuspendTv.setGravity(Gravity.CENTER);
    }

}
