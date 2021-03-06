package com.example.xiaoniu.publicuseproject.ExpandableListView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

import com.example.xiaoniu.publicuseproject.R;

public class ExpandableGridView extends ExpandableListView implements ExpandableListView.OnGroupClickListener {

    private OnGridItemClickListener listener;
    private boolean isOverwriteOnMeasure;
    private boolean isGroupClickable;
    private int horizontalSpacing;
    private int verticalSpacing;

    public ExpandableGridView(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnGroupClickListener(this);
        if (attrs == null)
            return;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableGridView);
        isOverwriteOnMeasure = a.getBoolean(R.styleable.ExpandableGridView_overwrite_measure, false);
        isGroupClickable = a.getBoolean(R.styleable.ExpandableGridView_group_clickable, true);
        horizontalSpacing = a.getDimensionPixelOffset(R.styleable.ExpandableGridView_horizontal_spacing, 0);
        verticalSpacing = a.getDimensionPixelOffset(R.styleable.ExpandableGridView_vertical_spacing, 0);
        a.recycle();
    }

    @Deprecated
    @Override
    public void setAdapter(ListAdapter adapter) {
        throw new RuntimeException(
                "For ExpandableGridView, use setExpandableGridAdapter(ExpandableGridAdapter) instead of " +
                        "setAdapter(ListAdapter)");
    }

    @Deprecated
    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        throw new RuntimeException(
                "For ExpandableGridView, use setExpandableGridAdapter(ExpandableGridAdapter) instead of " +
                        "setAdapter(ExpandableListAdapter)");
    }

    public void setExpandableGridAdapter(ExpandableGridAdapter adapter) {
        super.setAdapter(adapter);
        getExpandableGridAdapter().horizontalSpacing = horizontalSpacing;
        getExpandableGridAdapter().verticalSpacing = verticalSpacing;
        getExpandableGridAdapter().listener = listener;
    }

    @Deprecated
    @Override
    public ListAdapter getAdapter() {
        return super.getAdapter();
    }

    @Deprecated
    @Override
    public ExpandableListAdapter getExpandableListAdapter() {
        throw new RuntimeException(
                "For ExpandableGridView, use getExpandableGridAdapter() instead of getExpandableListAdapter()");
    }

    public ExpandableGridAdapter getExpandableGridAdapter() {
        return (ExpandableGridAdapter) super.getExpandableListAdapter();
    }

    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().horizontalSpacing = horizontalSpacing;
    }

    public int getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().verticalSpacing = verticalSpacing;
    }

    public boolean isOverwriteOnMeasure() {
        return isOverwriteOnMeasure;
    }

    public void setOverwriteOnMeasure(boolean overwriteOnMeasure) {
        isOverwriteOnMeasure = overwriteOnMeasure;
    }

    public boolean isGroupClickable() {
        return isGroupClickable;
    }

    public void setGroupClickable(boolean groupClickable) {
        isGroupClickable = groupClickable;
    }

    public void setOnGridItemClickListener(OnGridItemClickListener listener) {
        this.listener = listener;
        if (getExpandableGridAdapter() != null)
            getExpandableGridAdapter().listener = listener;
    }

    public OnGridItemClickListener getOnGridItemClickListener() {
        return listener;
    }

    public void expandAll() {
        expandAll(false);
    }

    public void expandAll(boolean animate) {
        if (getExpandableGridAdapter() == null) {
            return;
        }
        int count = getExpandableGridAdapter().getGroupCount();
        for (int groupPosition = 0; groupPosition < count; groupPosition++)
            expandGroup(groupPosition, animate);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = heightMeasureSpec;
        if (isOverwriteOnMeasure)
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return !isGroupClickable;
    }
}