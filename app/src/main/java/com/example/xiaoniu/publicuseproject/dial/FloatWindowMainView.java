package com.example.xiaoniu.publicuseproject.dial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xiaoniu.publicuseproject.R;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by panwenjuan on 17-8-9.
 */
public class FloatWindowMainView extends FrameLayout {

    private static boolean DBG = true;
    private static final String TAG = "FloatMainView";

    private static final int TIME = 3000;// 3秒后开始贴边
    private static final int LONG_TIME = 5000;// 5秒后开始贴边
    private static final int MOVE_DISTANCE = 20;// 移动距离
    private static final int DELAY_TIME = 1000;

    // 子窗口显示位置
    public static final int LEFT_TOP = 1;
    public static final int CENTER_TOP = 2;
    public static final int RIGHT_TOP = 3;
    public static final int LEFT_CENTER = 4;
    public static final int CENTER = 5;
    public static final int RIGHT_CENTER = 6;
    public static final int LEFT_BOTTOM = 7;
    public static final int CENTER_BOTTOM = 8;
    public static final int RIGHT_BOTTOM = 9;
    private int position = RIGHT_CENTER;
    private boolean isPortrait = true;
    private boolean canVibrator = true;

    private Context mContext;

    private FloatWindowManager mFloatWindowManager;
    private WindowManager mWindowManager;
    private FloatWindowHideView mFloatWindowHideView;// 隐藏窗
    private WindowManager.LayoutParams mWindowParams;
    private WindowManager.LayoutParams mMenuViewWindowParams;
    private WindowManager.LayoutParams mHideViewWindowParams;

    private ImageView mMainIv;
    private MyHandler handler = new MyHandler();// 透明化定时
//	private TranslateAnimation mClingAnimation;// 悬浮窗贴边动画

    // 屏幕宽高
    private int mScreenWidth;
    private int mScreenHeight;

    // 窗体宽高
    public int mWindowWidth;
    public int mWindowHeight;

    // 当前移动X轴,Y轴
    private float mNowX;
    private float mNowY;

    // 之前位置X轴,Y轴
    private float mTouchStartX;
    private float mTouchStartY;

    // 隐藏窗X轴,Y轴
    private int mViewHideX;
    private int mViewHideY;

    private boolean isMove = false;//是否正在移动
    private boolean isCling = false;// 是否贴边
    private boolean isMoveComeHide = false;// 是否移动在隐藏窗口上
    private boolean isAddedMenu = false;// 是否有显示菜单栏窗口


    public FloatWindowMainView(Context context) {
        super(context);
        this.mContext = context;
        mFloatWindowManager = FloatWindowManager.getInstance(mContext);
        mWindowManager = mFloatWindowManager.getWindowManager();
        mFloatWindowHideView = mFloatWindowManager.getFloatWindowHideView();
        mMenuViewWindowParams = mFloatWindowManager.getLayoutParams(mFloatWindowManager.VIEW_MENU);
//        Log.d(TAG, "mMenuViewWindowParams.x = " + mMenuViewWindowParams.x + "   mMenuViewWindowParams.y = " + mMenuViewWindowParams.y);
        LayoutInflater.from(mContext).inflate(R.layout.float_view_main, this);
        View view = findViewById(R.id.ll_main);
        mWindowWidth = view.getLayoutParams().width;
        mWindowHeight = view.getLayoutParams().height;
        mMainIv = (ImageView) findViewById(R.id.iv_main);
        mMainIv.setVisibility(View.VISIBLE);

        setAlpha();

        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mScreenHeight = ScreenUtils.getScreenHeight(context);

        mHideViewWindowParams = mFloatWindowManager.getLayoutParams(mFloatWindowManager.VIEW_HIDE);
        mViewHideX = (mScreenWidth - ScreenUtils.dip2px(context, 56)) / 2;
        mViewHideY = mScreenHeight - ScreenUtils.dip2px(mContext, 156);

        handler.postDelayed(clingBoundaryRunnable, TIME);

    }

    public void setWindowParams(WindowManager.LayoutParams mWindowParams) {
        this.mWindowParams = mWindowParams;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mNowX = event.getRawX();
        mNowY = event.getRawY() - ScreenUtils.getStatusHeight(mContext);
        switch (event.getAction()) {
            case MotionEvent.ACTION_OUTSIDE:
                if (isAddedMenu) {
                    isAddedMenu = false;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(clingBoundaryRunnable);
                handler.removeCallbacks(transparentRunnable);
                isMove = false;
                canVibrator = true;
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                setAlpha();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchStartY - event.getY() > MOVE_DISTANCE
                        || mTouchStartY - event.getY() < -MOVE_DISTANCE
                        || mTouchStartX - event.getX() > MOVE_DISTANCE
                        || mTouchStartX - event.getX() < -MOVE_DISTANCE) {
                    isMove = true;
                    if (isAddedMenu) {
                        isAddedMenu = false;
                    }
                    if (canVibrator) {
                        updateViewPosition();
                    }

                    if (mFloatWindowManager.getFloatWindowHideView().getVisibility() == View.GONE) {
                        mFloatWindowManager.getFloatWindowHideView().setVisibility(View.VISIBLE);
                    }

                    // 检测是否移动在隐藏窗口上
                    if (isCollsionWithRect(mViewHideX, mViewHideY, mFloatWindowHideView.getWidth(), mFloatWindowHideView.getHeight()
                            , mWindowParams.x, mWindowParams.y, mWindowWidth, mWindowHeight)) {
                        isMoveComeHide = true;
                        mFloatWindowManager.getFloatWindowHideView().updateHideView(true);
                        updateHideViewToCenter();
                    } else {
                        canVibrator = true;
                        isMoveComeHide = false;
                        mFloatWindowManager.getFloatWindowHideView().updateHideView(false);
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                mFloatWindowHideView.setVisibility(View.GONE);
                if (isMoveComeHide) {
                    mFloatWindowManager.removeMainWindow();
                } else {
                    judgeScreenEdge();
                    if (mTouchStartY - event.getY() > MOVE_DISTANCE
                            || mTouchStartY - event.getY() < -MOVE_DISTANCE
                            || mTouchStartX - event.getX() > MOVE_DISTANCE
                            || mTouchStartX - event.getX() < -MOVE_DISTANCE) {
                        mTouchStartX = mTouchStartY = 0;
                    } else {
                        if (isAddedMenu) {
                            isAddedMenu = false;
                        } else if (!isMove) {
//                            Toast.makeText(mContext, "click me", Toast.LENGTH_SHORT).show();
                            isAddedMenu = true;
                            EventBus.getDefault().post(new DataSynEvent());
                        }
                    }
                    mainMoveEnd();
                    updateMenuViewPosition();
                }
                isCling = false;
                break;
        }
        return true;
    }

    private void updateHideViewToCenter() {
       if (canVibrator) {
           canVibrator = false;
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   mWindowParams.x = (ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 50)) / 2;
                   mWindowParams.y = ScreenUtils.getScreenHeight(mContext) - ScreenUtils.dip2px(mContext, 154);
                   updateViewLayout(mFloatWindowManager.getFloatWindowMainView(), mWindowParams);
               }
           }, 500);
       }
    }

    // 更新浮动窗口位置参数
    private void updateViewPosition() {
        mWindowParams.x = (int) (mNowX - mTouchStartX);
        mWindowParams.y = (int) (mNowY - mTouchStartY);
        // 限制悬浮滑动范围
        viewMoveRestrict();
        updateViewLayout(mFloatWindowManager.getFloatWindowMainView(), mWindowParams);
    }

    private void updateViewLayout(View view, WindowManager.LayoutParams mWindowParams) {
        if (null == view || null == mWindowParams) {
            return;
        }
        try {
            mWindowManager.updateViewLayout(view, mWindowParams);
        } catch (Exception e) {
            mWindowManager.addView(view, mWindowParams);
            mWindowManager.updateViewLayout(view, mWindowParams);
        }
    }

    private void updateMenuViewPosition() {
        switch (position) {
            case LEFT_TOP:
                mMenuViewWindowParams.x = mWindowParams.x;
                mMenuViewWindowParams.y = mWindowParams.y;
                break;
            case LEFT_CENTER:
                mMenuViewWindowParams.x = mWindowParams.x + ScreenUtils.dip2px(mContext, 5);
                mMenuViewWindowParams.y = mWindowParams.y + mMainIv.getHeight() / 2;
                break;
            case LEFT_BOTTOM:
                mMenuViewWindowParams.x = mWindowParams.x;
                mMenuViewWindowParams.y = mWindowParams.y + mMainIv.getHeight();
                break;
            case RIGHT_TOP:
                mMenuViewWindowParams.x = mWindowParams.x + mMainIv.getWidth();
                mMenuViewWindowParams.y = mWindowParams.y;
                break;
            case RIGHT_CENTER:
                    mMenuViewWindowParams.x = mWindowParams.x + mMainIv.getWidth();
                mMenuViewWindowParams.y = mWindowParams.y + mMainIv.getHeight() / 2;
                break;
            case RIGHT_BOTTOM:
                mMenuViewWindowParams.x = mWindowParams.x + mMainIv.getWidth();
                mMenuViewWindowParams.y = mWindowParams.y + mMainIv.getHeight();
                break;
            case CENTER_TOP:
                mMenuViewWindowParams.x = mWindowParams.x + mMainIv.getWidth() / 2;
                mMenuViewWindowParams.y = mWindowParams.y;
                break;
            case CENTER_BOTTOM:
                mMenuViewWindowParams.x = mWindowParams.x + mMainIv.getWidth() / 2;
                mMenuViewWindowParams.y = mWindowParams.y + mMainIv.getHeight();
                break;
            case CENTER:
                break;
        }
        if (isAddedMenu) {
            mMenuViewWindowParams.dimAmount = 0.4f;
        } else {
            mMenuViewWindowParams.dimAmount = 0.0f;
        }
    }

    private void viewMoveRestrict() {
        if (mWindowParams.x >= mScreenWidth - mMainIv.getWidth()) {
            mWindowParams.x = mScreenWidth - mMainIv.getWidth();
        } else if (mWindowParams.x < 0) {
            mWindowParams.x = 0;
        }
        if (mWindowParams.y > mScreenHeight - mMainIv.getHeight()) {
            mWindowParams.y = mScreenHeight - mMainIv.getHeight();
        } else if (mWindowParams.y < 0) {
            mWindowParams.y = 0;
        }
    }

    // 移动结束后的定位
    public void mainMoveEnd() {
        if (null == mFloatWindowManager.getFloatWindowMainView()) {
            return;
        }

        switch (position) {
            case LEFT_TOP:
                mWindowParams.y = 0;
                break;
            case LEFT_CENTER:
                mWindowParams.x = 0;
                break;
            case LEFT_BOTTOM:
                mWindowParams.y = mScreenHeight - mMainIv.getHeight();
                break;
            case RIGHT_TOP:
                mWindowParams.y = 0;
                break;
            case RIGHT_CENTER:
                mWindowParams.x = mScreenWidth - mMainIv.getWidth();
                break;
            case RIGHT_BOTTOM:
                mWindowParams.y = mScreenHeight - mMainIv.getHeight();
                break;
            case CENTER_TOP:
                mWindowParams.y = 0;
                break;
            case CENTER_BOTTOM:
                mWindowParams.y = mScreenHeight - mMainIv.getHeight();
                break;
        }

        updateViewLayout(mFloatWindowManager.getFloatWindowMainView(), mWindowParams);
        timerAgain();
    }

    // 透明化定时
    private void timerAgain() {
        if (null == mFloatWindowManager.getFloatWindowMainView()) {
            return;
        }
        if (null != handler) {
            handler.removeCallbacks(clingBoundaryRunnable);
            if (isAddedMenu) {
                handler.postDelayed(clingBoundaryRunnable, LONG_TIME);
            } else {
                handler.postDelayed(clingBoundaryRunnable, TIME);
            }
        }
    }

    /**
     * 矩形碰撞的函数
     *
     * @param x1 第一个矩形的X坐标
     * @param y1 第一个矩形的Y坐标
     * @param w1 第一个矩形的宽
     * @param h1 第一个矩形的高
     * @param x2 第二个矩形的X坐标
     * @param y2 第二个矩形的Y坐标
     * @param w2 第二个矩形的宽
     * @param h2 第二个矩形的高
     */
    public boolean isCollsionWithRect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        if ((x1 - w2) < x2 && x2 < (x1 + w1)
                && (y1 - h2) < y2 && y2 < (y1 + h1)) {
            return true;
        } else {
            return false;
        }
    }

    // 还原透明度
    private void setAlpha() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMainIv.setImageAlpha(255);
        } else {
            mMainIv.setAlpha(255);
        }
    }

    // 悬浮窗贴边线程
    protected Runnable clingBoundaryRunnable = new Runnable() {
        @Override
        public void run() {
            suspendClingBoundary();

            // 两秒后透明
            handler.postDelayed(transparentRunnable, 2000);

            // 菜单栏
            if (isAddedMenu) {
                isAddedMenu = false;
            }
        }
    };

    // 贴边后的半透明处理
    protected Runnable transparentRunnable = new Runnable() {
        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mMainIv.setImageAlpha(128);
            } else {
                mMainIv.setAlpha(128);
            }
        }
    };

    // 判断屏幕两边
    private void judgeScreenEdge() {
        int wmX = mWindowParams.x;
        int wmY = mWindowParams.y;

        if (isPortrait) {
            if (wmX <= mScreenWidth / 2) {//left
                if (wmY < mScreenHeight / 7) {
                    if (wmX < mScreenWidth / 3) {
                        position = LEFT_TOP;
                    } else {
                        position = CENTER_TOP;
                    }
                } else if (wmY >= mScreenHeight / 7 && wmY <= mScreenHeight * 4 / 5) {
                    position = LEFT_CENTER;
                } else {
                    if (wmX < mScreenWidth / 3) {
                        position = LEFT_BOTTOM;
                    } else {
                        position = CENTER_BOTTOM;
                    }
                }
            } else {//right
                if (wmY < mScreenHeight / 7) {
                    if (wmX < mScreenWidth * 2 / 3) {
                        position = CENTER_TOP;
                    } else {
                        position = RIGHT_TOP;
                    }
                } else if (wmY >= mScreenHeight / 7 && wmY <= mScreenHeight * 4 / 5) {
                    position = RIGHT_CENTER;
                } else {
                    if (wmX < mScreenWidth * 2 / 3) {
                        position = CENTER_BOTTOM;
                    } else {
                        position = RIGHT_BOTTOM;
                    }
                }
            }
        } else {//land
            if (wmX <= mScreenWidth / 2) {//left
                if (wmY < mScreenHeight / 5) {
                    if (wmX < mScreenWidth / 7) {
                        position = LEFT_TOP;
                    } else {
                        position = CENTER_TOP;
                    }
                } else if (wmY >= mScreenHeight / 5 && wmY <= mScreenHeight * 5 / 7) {//
                    position = LEFT_CENTER;
                } else {
                    if (wmX < mScreenWidth / 7) {
                        position = LEFT_BOTTOM;
                    } else {
                        position = CENTER_BOTTOM;
                    }
                }
            } else {//right
                if (wmY < mScreenHeight / 5) {
                    if (wmX < mScreenWidth * 5 / 6) {
                        position = CENTER_TOP;
                    } else {
                        position = RIGHT_TOP;
                    }
                } else if (wmY >= mScreenHeight / 5 && wmY <= mScreenHeight * 5 / 7) {
                    position = RIGHT_CENTER;
                } else {
                    if (wmX < mScreenWidth * 5 / 6) {
                        position = CENTER_BOTTOM;
                    } else {
                        position = RIGHT_BOTTOM;
                    }
                }
            }
        }
    }

    // 贴边
    private void suspendClingBoundary() {
        if (position == LEFT_TOP) {
            mWindowParams.y = ScreenUtils.dip2px(mContext, -25);
        } else if (position == LEFT_CENTER) {
            mWindowParams.x = ScreenUtils.dip2px(mContext, -25);
        } else if (position == LEFT_BOTTOM) {
            mWindowParams.y = mScreenHeight - mMainIv.getHeight() + ScreenUtils.dip2px(mContext, 25);
        } else if (position == RIGHT_TOP) {
            mWindowParams.y = ScreenUtils.dip2px(mContext, -25);
        } else if (position == RIGHT_CENTER) {
            mWindowParams.x = mScreenWidth - mMainIv.getWidth() + ScreenUtils.dip2px(mContext, 25);
        } else if (position == RIGHT_BOTTOM) {
            mWindowParams.y = mScreenHeight - mMainIv.getHeight() + ScreenUtils.dip2px(mContext, 25);
        } else if (position == CENTER_TOP) {
            mWindowParams.y = ScreenUtils.dip2px(mContext, -25);
        } else if (position == CENTER_BOTTOM) {
            mWindowParams.y = mScreenHeight - mMainIv.getHeight() + ScreenUtils.dip2px(mContext, 25);
        }

        if (isAddedMenu) {
            mWindowParams.dimAmount = 0.0f;
        }
        updateViewLayout(mFloatWindowManager.getFloatWindowMainView(), mWindowParams);
        isCling = true;
    }

    public int getPosition() {
        return position;
    }

    public void setAddedMenu(boolean isAddedMenu) {
        this.isAddedMenu = isAddedMenu;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        Log.d(TAG, "onConfigurationChanged()...");
        if (newConfig == null) {
            return;
        }

        try {
            switch (newConfig.orientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    isPortrait = false;
                    mScreenWidth = ScreenUtils.getScreenWidth(mContext);
                    mScreenHeight = ScreenUtils.getScreenHeight(mContext);
                    mViewHideX = (mScreenWidth - ScreenUtils.dip2px(mContext, 56)) / 2;
                    mViewHideY = mScreenHeight - ScreenUtils.dip2px(mContext, 156);
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    isPortrait = true;
                    mScreenWidth = ScreenUtils.getScreenWidth(mContext);
                    mScreenHeight = ScreenUtils.getScreenHeight(mContext);
                    mViewHideX = (mScreenWidth - ScreenUtils.dip2px(mContext, 56)) / 2;
                    mViewHideY = mScreenHeight - ScreenUtils.dip2px(mContext, 156);
                    break;
                default:
                    break;
            }
            position = 6;
            mWindowParams.y = mScreenHeight * 3 / 4 - ScreenUtils.getStatusBarHeight(mContext);
            mainMoveEnd();
            updateMenuViewPosition();
            mHideViewWindowParams.x = (mScreenWidth - ScreenUtils.dip2px(mContext, 56)) / 2;
            mHideViewWindowParams.y = mScreenHeight - ScreenUtils.dip2px(mContext, 120);
            updateViewLayout(mFloatWindowHideView, mHideViewWindowParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyHandler extends Handler {

        public MyHandler() {};

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            }
        }
    }
}
