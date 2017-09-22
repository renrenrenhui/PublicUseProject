package com.example.xiaoniu.publicuseproject.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
    private final Context context;
    private final ImageView img;

    public getImageCacheAsyncTask(Context context, ImageView image) {
        this.context = context;
        this.img = image;
    }

    @Override
    protected File doInBackground(String... params) {
        String imgUrl =  params[0];
        try {
            return Glide.with(context)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception ex) {
            Log.e("glide", "Exception: ");
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        //此path就是对应文件的缓存路径
        String path = result.getPath();
        Log.e("glide", "path: " + path);
        Bitmap bmp= BitmapFactory.decodeFile(path);
        img.setImageBitmap(bmp);

    }
}