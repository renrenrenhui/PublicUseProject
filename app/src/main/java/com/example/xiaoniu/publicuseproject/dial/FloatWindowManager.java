package com.example.xiaoniu.publicuseproject.dial;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;



/**
 * Created by panwenjuan on 17-8-9.
 */
public class FloatWindowManager {

    private static String TAG = "FloatWindowManager";
    private static boolean DBG = true;
    public final int VIEW_MAIN = 0;
    public final int VIEW_MENU = 1;
    public final int VIEW_HIDE = 2;
    public final int VIEW_REMIND = 3;

    private static FloatWindowManager mFloatWindowManager;
    private Context mContext;
    private WindowManager mWindowManager;
    private static WindowManager.LayoutParams mWindowParams;

    private FloatWindowMainView mFloatWindowMainView;
    private FloatWindowHideView mFloatWindowHideView;

    //Whether it is completely hidden
    private boolean isFullHide = false;

    private FloatWindowManager(Context context) {
        this.mContext = context;
    }

    public static FloatWindowManager getInstance(Context context) {
        if (mFloatWindowManager == null) {
            mFloatWindowManager = new FloatWindowManager(context.getApplicationContext());
        }
        return mFloatWindowManager;
    }

    //Initialize the window manager
    public WindowManager getWindowManager() {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public void setWindowParams(WindowManager.LayoutParams params) {
        this.mWindowParams = params;
    }

    //Initialize the suspension window parameters
    public WindowManager.LayoutParams getLayoutParams(int type) {
        WindowManager.LayoutParams wmLayoutParams = new WindowManager.LayoutParams();
        wmLayoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_DIM_BEHIND
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        wmLayoutParams.type = getWindowFlag();
        wmLayoutParams.format = PixelFormat.RGBA_8888;
        //悬浮窗背景明暗度0~1，数值越大背景越暗，只有在flags设置了WindowManager.LayoutParams.FLAG_DIM_BEHIND 这个属性才会生效
        wmLayoutParams.dimAmount = 0.0f;
        wmLayoutParams.alpha = 1.0f;

        if (type == VIEW_MAIN || type == VIEW_MENU) {
            wmLayoutParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
            wmLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            wmLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

            // Set the initial value of x, y to the upper left corner of the screen as the origin
            if (type == VIEW_MAIN) {
                wmLayoutParams.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                wmLayoutParams.x = ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 50);
                wmLayoutParams.y = ScreenUtils.getScreenWidth(mContext) * 3 / 4 - ScreenUtils.getStatusBarHeight(mContext);
            } else {
                wmLayoutParams.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                wmLayoutParams.x = ScreenUtils.getScreenWidth(mContext) - 385;
                wmLayoutParams.y = ScreenUtils.getScreenWidth(mContext) * 3 / 4 - ScreenUtils.getStatusBarHeight(mContext) - 140;
            }

        } else if (type == VIEW_HIDE) {
            wmLayoutParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至下方
            wmLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            wmLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

            // Set the initial value of x, y to the upper left corner of the screen as the origin
            wmLayoutParams.x = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 56)) / 2;
            wmLayoutParams.y = ScreenUtils.getScreenHeight(mContext) - ScreenUtils.dip2px(mContext, 156);

        } else if (type == VIEW_REMIND) {
            wmLayoutParams.gravity = Gravity.CENTER; // 调整悬浮窗口至中间
            wmLayoutParams.width = ScreenUtils.dip2px(mContext, 300);
            wmLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        return wmLayoutParams;
    }

    public void setIsFullHide(boolean isFullHide) {
        this.isFullHide = isFullHide;
    }

    public boolean isWindowShowing() {
        return mFloatWindowMainView != null || mFloatWindowHideView != null;
    }

    public void showFloatWindow(Context context) {
        Log.d(TAG, "showFloatWindow() enter...");

        if (isWindowShowing()) {
            if (mFloatWindowHideView != null && mFloatWindowMainView == null) {
                removeHideWindow();
            } else {
                Log.d(TAG, "alert window already show...");
                return;
            }
        }

        setIsFullHide(false);
        createMainWindow(context);
        Log.d(TAG, "showFloatWindow() exit...");
    }

    public void destroyFloatWindow() {
        setIsFullHide(true);
        removeMainWindow();
    }

    //create main window
    private void createMainWindow(Context context) {
        Log.d(TAG, "createMainWindow()  enter");
        /*if (isFullHide) {
            return;
        }*/
        WindowManager windowManager = getWindowManager();
        mFloatWindowMainView = new FloatWindowMainView(context.getApplicationContext());
        mWindowParams = getLayoutParams(VIEW_MAIN);
        mFloatWindowMainView.setWindowParams(mWindowParams);
        try {
            windowManager.addView(mFloatWindowMainView, mWindowParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create a hidden window in advance
        if (mFloatWindowHideView == null) {
            createHideWindow(context.getApplicationContext());
        }
        Log.d(TAG, "createMainWindow()  exit ---> ");
    }

    //create hide window
    public void createHideWindow(Context context) {
        WindowManager windowManager = getWindowManager();
        if (mFloatWindowHideView == null) {
            mFloatWindowHideView = new FloatWindowHideView(context);
        }
        mFloatWindowHideView.setVisibility(View.GONE);
        try {
            windowManager.addView(mFloatWindowHideView, getLayoutParams(VIEW_HIDE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMainWindow() {

        try {
            if (mFloatWindowMainView != null) {
                WindowManager windowManager = getWindowManager();
                windowManager.removeView(mFloatWindowMainView);
                mFloatWindowMainView = null;
            }
            mWindowParams = null;
            removeHideWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeHideWindow() {
        try {
            if (mFloatWindowHideView != null) {
                WindowManager windowManager = getWindowManager();
                windowManager.removeView(mFloatWindowHideView);
                mFloatWindowHideView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FloatWindowMainView getFloatWindowMainView() {
        return mFloatWindowMainView;
    }

    public FloatWindowHideView getFloatWindowHideView() {
        if (mFloatWindowHideView == null) {
            createHideWindow(mContext);
        }
        return mFloatWindowHideView;
    }

    public static boolean isHoldAlertWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                return false;
            } else {
                // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
                return true;
            }
        } else {
            return true;
        }

    }

    public void setVisibilityFloatWindow(int isVisibility) {
        if (mFloatWindowMainView != null) {
            mFloatWindowMainView.setVisibility(isVisibility);
        }
    }

    public static int getWindowFlag() {
//        if (Build.VERSION.SDK_INT >= 26) {
//            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
            return WindowManager.LayoutParams.TYPE_PHONE;
//        }
    }
}
