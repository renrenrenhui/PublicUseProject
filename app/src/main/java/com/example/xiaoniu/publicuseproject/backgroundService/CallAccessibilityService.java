package com.example.xiaoniu.publicuseproject.backgroundService;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;


public class CallAccessibilityService extends AccessibilityService {

    private static String TAG = "AccessibilityService";
    private static boolean DBG = false;

    @Override
    public void onInterrupt() {
        if (DBG) Log.d(TAG, "CallAccessibilityService    onInterrupt()...");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        if (DBG) Log.d(TAG, "CallAccessibilityService    onServiceConnected()...");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        CharSequence packageName = event.getPackageName();
        if (TextUtils.isEmpty(packageName)) return;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
