package com.example.xiaoniu.publicuseproject.accessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    private final String TAG = "MyAccessibilityService";  
  
    @Override  
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();  
        String eventTypeName = "";  
        switch (eventType) {  
        case AccessibilityEvent.TYPE_VIEW_CLICKED:  
            eventTypeName = "TYPE_VIEW_CLICKED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_FOCUSED:  
            eventTypeName = "TYPE_VIEW_FOCUSED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:  
            eventTypeName = "TYPE_VIEW_LONG_CLICKED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_SELECTED:  
            eventTypeName = "TYPE_VIEW_SELECTED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:  
            eventTypeName = "TYPE_VIEW_TEXT_CHANGED";  
            break;  
        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:  
            eventTypeName = "TYPE_WINDOW_STATE_CHANGED";  
            break;  
        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:  
            eventTypeName = "TYPE_NOTIFICATION_STATE_CHANGED";  
            break;  
        case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:  
            eventTypeName = "TYPE_TOUCH_EXPLORATION_GESTURE_END";  
            break;  
        case AccessibilityEvent.TYPE_ANNOUNCEMENT:  
            eventTypeName = "TYPE_ANNOUNCEMENT";  
            break;  
        case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:  
            eventTypeName = "TYPE_TOUCH_EXPLORATION_GESTURE_START";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:  
            eventTypeName = "TYPE_VIEW_HOVER_ENTER";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:  
            eventTypeName = "TYPE_VIEW_HOVER_EXIT";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_SCROLLED:  
            eventTypeName = "TYPE_VIEW_SCROLLED";  
            break;  
        case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:  
            eventTypeName = "TYPE_VIEW_TEXT_SELECTION_CHANGED";  
            break;  
        case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:  
            eventTypeName = "TYPE_WINDOW_CONTENT_CHANGED";  
            break;  
        }  
        Log.e(TAG, "onAccessibilityEvent eventType:" + eventType + ", eventTypeName:" + eventTypeName);
    }
  
    @Override  
    public void onInterrupt() {  
        // TODO Auto-generated method stub  
        Log.e(TAG, "onInterrupt");
    }  
}