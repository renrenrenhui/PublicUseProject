package com.example.xiaoniu.publicuseproject.dial;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by panwenjuan on 17-8-9.
 */
public class ScreenUtils {

    /**
     * Get the width of the screen
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }
    public static int getScreenWidthRealFull(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * Get the height of the screen
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels - getStatusBarHeight(context);
        return screenHeight;
    }

    public static int getScreenHeightFull(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }
    public static int getScreenHeightRealFull(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getStatusBarHeight(Context context) {
        int Identifier = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (Identifier > 0) {
            return context.getResources().getDimensionPixelSize(Identifier);
        }
        return 0;
    }

    public static int getDensityDpi(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isScreenChange(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration();
        int ori = mConfiguration.orientation;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            return false;
        }
        //        return AppConfig.mhrOrientation == MHROrientation.LANDSCAPE;
        return false;
    }

    public static int getScreenRotation(Context context) {
        return  ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

}
