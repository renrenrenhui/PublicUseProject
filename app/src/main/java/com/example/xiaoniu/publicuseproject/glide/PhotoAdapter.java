package com.example.xiaoniu.publicuseproject.glide;

import com.example.xiaoniu.publicuseproject.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private String[] images;
    private int mScreeWidth;

    public PhotoAdapter(Context context, String[] images) {
        this.images = images;
        this.mContext = context;
        mScreeWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public int getCount() {

        return images.length;
    }

    @Override
    public Object getItem(int arg0) {

        return images[arg0];
    }

    @Override
    public long getItemId(int arg0) {

        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater infaInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();
            convertView = infaInflater.inflate(R.layout.photo_layout, null);
            holder.image = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String uri = (String) getItem(position);

        new getImageCacheAsyncTask(mContext, holder.image).execute(uri);
//        Glide.with(mContext).load(uri).into(holder.image);
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }

}
