/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.xiaoniu.publicuseproject.dial;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * Provides static functions to work with views
 */
public class ViewUtil {
    private ViewUtil() {}

    public static void resizeText(TextView textView, int originalTextSize, int minTextSize) {
        final Paint paint = textView.getPaint();
        final int width = textView.getWidth();
        if (width == 0) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, originalTextSize);
        float ratio = width / paint.measureText(textView.getText().toString());
        if (ratio <= 1.0f) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    Math.max(minTextSize, originalTextSize * ratio));
        }
    }

    public static int getLayoutDirection(Context context){
        Class<View> c = View.class;
        Method method = null;
        int type = View.LAYOUT_DIRECTION_LTR;
        try {
            method = c.getMethod("getLayoutDirection");
            type = (int)method.invoke(new View(context),(Object[])null);
        }catch (Exception e){
            Log.d(context.getClass().getName(), e + "  type = View.LAYOUT_DIRECTION_LTR");
            type = View.LAYOUT_DIRECTION_LTR;
        }finally {
            return type;
        }
    }
}
