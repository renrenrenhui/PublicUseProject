package com.example.xiaoniu.publicuseproject.viewLifeCycle;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout {
    public CustomRelativeLayout(Context context) {
        super(context);
        Log.e("view", "construct 1 parameters .");
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("view", "construct 2 parameters .");
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("view", "construct 3 parameters .");
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.e("view", "onVisibilityChanged");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("view", "onAttachedToWindow");
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("view", "onConfigurationChanged");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("view", "onDetachedFromWindow");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("view", "onDraw");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("view", "onFinishInflate");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("view", "onLayout");
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        Log.e("view", "onFocusChanged");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("view", "onMeasure");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.e("view", "onWindowVisibilityChanged");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("view", "onSizeChanged");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.e("view", "onWindowFocusChanged");
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        Log.e("view", "onRtlPropertiesChanged");
    }
}

/*
--------  android:visibility="visible"
09-01 11:08:34.844 29699-29699/com.example.xiaoniu.publicuseproject E/view: construct 2 parameters .
09-01 11:08:34.844 29699-29699/com.example.xiaoniu.publicuseproject E/view: onFinishInflate
09-01 11:08:34.870 29699-29699/com.example.xiaoniu.publicuseproject E/view: onRtlPropertiesChanged
09-01 11:08:34.870 29699-29699/com.example.xiaoniu.publicuseproject E/view: onRtlPropertiesChanged
09-01 11:08:34.870 29699-29699/com.example.xiaoniu.publicuseproject E/view: onAttachedToWindow(将view加到window中)
09-01 11:08:34.871 29699-29699/com.example.xiaoniu.publicuseproject E/view: onWindowVisibilityChanged
09-01 11:08:34.871 29699-29699/com.example.xiaoniu.publicuseproject E/view: onVisibilityChanged
09-01 11:08:34.873 29699-29699/com.example.xiaoniu.publicuseproject E/view: onMeasure(测量view的长宽)
09-01 11:08:34.916 29699-29699/com.example.xiaoniu.publicuseproject E/view: onMeasure
09-01 11:08:34.916 29699-29699/com.example.xiaoniu.publicuseproject E/view: onSizeChanged
09-01 11:08:34.917 29699-29699/com.example.xiaoniu.publicuseproject E/view: onLayout(定位View在父View中的位置)
09-01 11:08:34.918 29699-29699/com.example.xiaoniu.publicuseproject E/view: onDraw(在window中绘制)------（只有可见的View才在window中绘制）
09-01 11:08:34.930 29699-29699/com.example.xiaoniu.publicuseproject E/view: onWindowFocusChanged
09-01 11:08:34.938 29699-29699/com.example.xiaoniu.publicuseproject E/view: onMeasure-----|
09-01 11:08:34.938 29699-29699/com.example.xiaoniu.publicuseproject E/view: onLayout------|（gone的View不执行）
09-01 11:08:34.938 29699-29699/com.example.xiaoniu.publicuseproject E/view: onDraw--------|
09-01 11:09:47.408 29699-29699/com.example.xiaoniu.publicuseproject E/view: onWindowVisibilityChanged
09-01 11:09:47.433 29699-29699/com.example.xiaoniu.publicuseproject E/view: onWindowFocusChanged
09-01 11:09:47.661 29699-29699/com.example.xiaoniu.publicuseproject E/view: onDetachedFromWindow(将view从window中移除)
--------

--------  android:visibility="invisible"
09-01 11:11:04.041 1398-1398/com.example.xiaoniu.publicuseproject E/view: construct 2 parameters .
09-01 11:11:04.041 1398-1398/com.example.xiaoniu.publicuseproject E/view: onFinishInflate
09-01 11:11:04.057 1398-1398/com.example.xiaoniu.publicuseproject E/view: onRtlPropertiesChanged
09-01 11:11:04.057 1398-1398/com.example.xiaoniu.publicuseproject E/view: onRtlPropertiesChanged
09-01 11:11:04.057 1398-1398/com.example.xiaoniu.publicuseproject E/view: onAttachedToWindow
09-01 11:11:04.057 1398-1398/com.example.xiaoniu.publicuseproject E/view: onWindowVisibilityChanged
09-01 11:11:04.057 1398-1398/com.example.xiaoniu.publicuseproject E/view: onVisibilityChanged
09-01 11:11:04.057 1398-1398/com.example.xiaoniu.publicuseproject E/view: onMeasure
09-01 11:11:04.107 1398-1398/com.example.xiaoniu.publicuseproject E/view: onMeasure
09-01 11:11:04.108 1398-1398/com.example.xiaoniu.publicuseproject E/view: onSizeChanged
09-01 11:11:04.108 1398-1398/com.example.xiaoniu.publicuseproject E/view: onLayout
09-01 11:11:04.116 1398-1398/com.example.xiaoniu.publicuseproject E/view: onWindowFocusChanged
09-01 11:11:04.124 1398-1398/com.example.xiaoniu.publicuseproject E/view: onMeasure
09-01 11:11:04.124 1398-1398/com.example.xiaoniu.publicuseproject E/view: onLayout
09-01 11:11:08.982 1398-1398/com.example.xiaoniu.publicuseproject E/view: onWindowVisibilityChanged
09-01 11:11:09.020 1398-1398/com.example.xiaoniu.publicuseproject E/view: onWindowFocusChanged
09-01 11:11:09.254 1398-1398/com.example.xiaoniu.publicuseproject E/view: onDetachedFromWindow
--------

--------  android:visibility="gone"
09-01 11:20:46.335 11696-11696/com.example.xiaoniu.publicuseproject E/view: construct 2 parameters .
09-01 11:20:46.335 11696-11696/com.example.xiaoniu.publicuseproject E/view: onFinishInflate
09-01 11:20:46.343 11696-11696/com.example.xiaoniu.publicuseproject E/view: onRtlPropertiesChanged
09-01 11:20:46.343 11696-11696/com.example.xiaoniu.publicuseproject E/view: onRtlPropertiesChanged
09-01 11:20:46.343 11696-11696/com.example.xiaoniu.publicuseproject E/view: onAttachedToWindow
09-01 11:20:46.343 11696-11696/com.example.xiaoniu.publicuseproject E/view: onWindowVisibilityChanged
09-01 11:20:46.343 11696-11696/com.example.xiaoniu.publicuseproject E/view: onVisibilityChanged
09-01 11:20:46.400 11696-11696/com.example.xiaoniu.publicuseproject E/view: onWindowFocusChanged
09-01 11:20:51.608 11696-11696/com.example.xiaoniu.publicuseproject E/view: onWindowVisibilityChanged
09-01 11:20:51.631 11696-11696/com.example.xiaoniu.publicuseproject E/view: onWindowFocusChanged
09-01 11:20:51.879 11696-11696/com.example.xiaoniu.publicuseproject E/view: onDetachedFromWindow
--------

综上所述：
关于View的生命周期有以下几个步骤：
1.Creation（创建）
  Constructors（构造函数）,在Code中实例化一个View会调用第一个构造函数，如果在xml中定义会调用第二个构造函数，而第三个函数系统是不调用的，要由View显式调用.
  onFinishInflate()：该方法当View及其子View从XML文件中加载完成后会被调用.
2.Layout（布局）
  onMeasure(int, int)：该方法在计算当前View及其所有子View尺寸大小需求时会被调用。
  onLayout(boolean, int, int, int, int)：该方法在当前View需要为其子View分配尺寸和位置时会被调用。
  onSizeChanged(int, int, int, int)：该方法在当前View尺寸变化时被调用。
3.Drawing（绘制）
  onDraw(android.graphics.Canvas)：该方法在当前View需要呈现其内容时被调用。
4.Event processing（事件处理）
  onKeyDown(int, KeyEvent)：该方法在一个物理按键事件发生时被调用。
  onKeyUp(int, KeyEvent)：该方法在一个物理按键弹起事件发生时被调用。
  onTrackballEvent(MotionEvent)：该方法在一个轨迹球运动事件发生时被调用。
  onTouchEvent(MotionEvent)：该方法在一个触摸屏幕运动事件发生时被调用。
5.Focus（聚焦）
  onFocusChanged(boolean, int, android.graphics.Rect)：该方法在当前View获得或失去焦点时被调用。
  onWindowFocusChanged(boolean)：该方法在包含当前View的window获得或失去焦点时被调用。
6.Attaching（附上）
  onAttachedToWindow()：该方法在当前View被附到一个window上时被调用。
  onDetachedFromWindow()：该方法在当前View从一个window上分离时被调用。
  onVisibilityChanged(View, int)：该方法在当前View或其祖先的可见性改变时被调用。
  onWindowVisibilityChanged(int)：该方法在包含当前View的window可见性改变时被调用。

View 的关键生命周期为:
 [改变可见性]  -->   构造View   -->      onFinishInflate  -->   onAttachedToWindow  -->  onMeasure  -->  onSizeChanged  -->  onLayout  -->   onDraw  -->  onDetackedFromWindow
 */