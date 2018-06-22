package com.example.xiaoniu.publicuseproject.ExpandableListView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

class XGridView extends GridView {

    public XGridView(Context context) {
        super(context);
    }

    public XGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}