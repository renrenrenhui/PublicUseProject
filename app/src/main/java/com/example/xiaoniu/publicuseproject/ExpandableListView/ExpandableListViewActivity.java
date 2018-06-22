package com.example.xiaoniu.publicuseproject.ExpandableListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewActivity extends Activity {

    //View
    private ExpandableListView expandableListView;
    //Model：定义的数据
    private String[] groups = {"A", "B", "C"};
    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}*/
    private String[][] childs = {{"A1", "A2", "A3", "A4"}, {"A1", "A2", "A3", "B4"}, {"A1", "A2", "A3", "C4"}};


    ExpandableGridView expandableGridView;
    SimpleExpandableGridAdapter<String> expandableGridAdapter;
    List<List<String>> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_listview);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new MyExpandableListView());

        expandableGridView = (ExpandableGridView) findViewById(R.id.egv);
        strings = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            temp.add("" + i);
        for (int i = 0; i < 10; i++)
            strings.add(temp);
        expandableGridView.setExpandableGridAdapter(expandableGridAdapter = new MyAdapter(strings));
        expandableGridView.expandAll(true);
        expandableGridView.setOnGridItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onGridItemClick(View view, int gridGroupPosition, int gridChildPosition) {
                String date = expandableGridAdapter.getData(gridGroupPosition, gridChildPosition);
//                Toast.makeText(MainActivity.this, "p:" + gridGroupPosition + ",c:" + gridChildPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return groups.length;
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            Log.d("smyhvae", "-->" + groupPosition);
            return childs[groupPosition].length;
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        //返回二级列表中的单个item（返回的是对象）
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs[groupPosition][childPosition];  //不要误写成groups[groupPosition][childPosition]
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
            }
            TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group);
            tv_group.setText(groups[groupPosition]);
            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
            }

            ImageView iv_child = (ImageView) convertView.findViewById(R.id.iv_child);
            TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);

            //iv_child.setImageResource(resId);
            tv_child.setText(childs[groupPosition][childPosition]);

            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    class MyAdapter extends SimpleExpandableGridAdapter<String> {

        public MyAdapter(List<List<String>> dataList) {
            super(dataList);
        }

        @Override
        public View getGridGroupView(int gridGroupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText("group:" + gridGroupPosition);
            return convertView;
        }

        @Override
        public View getGridChildView(int gridGroupPosition, int gridChildPosition, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText("child:" + getData(gridGroupPosition, gridChildPosition));
            return convertView;
        }

        @Override
        public int getNumColumns(int gridGroupPosition) {
            return gridGroupPosition % 3 + 3;
        }
    }

}