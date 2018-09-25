package com.example.xiaoniu.publicuseproject.recyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.List;

import drawthink.expandablerecyclerview.bean.RecyclerViewData;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView1;
    private UnitRecyclerAdapter mUnitRecyclerAdapter;
    private List<String> mUnitList = new ArrayList<>();
    private RecyclerView mRecyclerView2;
    private ScheduleRecyclerAdapter mScheduleRecyclerAdapter;
    List<Category> lists = new ArrayList<>();
    private List<RecyclerViewData> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mRecyclerView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        mUnitList.clear();
        mUnitList.add("haha");
        mUnitList.add("heihei");
        mUnitList.add("pig");
        mUnitList.add("go die");
        mUnitRecyclerAdapter = new UnitRecyclerAdapter(this, mUnitList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView1.setLayoutManager(linearLayoutManager);
        mRecyclerView1.setAdapter(mUnitRecyclerAdapter);
        mRecyclerView1.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_margin),
                getResources().getDimensionPixelSize(R.dimen.item_margin), getResources().getDimensionPixelSize(R.dimen.item_margin),
                getResources().getDimensionPixelSize(R.dimen.item_margin)));
        mUnitRecyclerAdapter.setOnItemClickListener(new UnitRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });


        lists.add(new Category("饼干", 0));
        lists.add(new Category("奶油饼干", 1));
        lists.add(new Category("威化", 1));
        lists.add(new Category("曲奇", 1));
        lists.add(new Category("威化", 1));
        lists.add(new Category("曲奇", 1));
        lists.add(new Category("传统糕点", 0));
        lists.add(new Category("凤梨酥", 1));
        lists.add(new Category("杏仁饼", 1));
        lists.add(new Category("烧饼", 1));
        lists.add(new Category("花生酥", 1));
        lists.add(new Category("西式糕点", 0));
        lists.add(new Category("巧克力派", 1));
        lists.add(new Category("酥心卷", 1));
        lists.add(new Category("面包", 1));
        lists.add(new Category("泡芙", 1));
        lists.add(new Category("蛋挞", 1));
        lists.add(new Category("巧克力派", 1));
        lists.add(new Category("酥心卷", 1));
        lists.add(new Category("面包", 1));
        lists.add(new Category("泡芙", 1));
        lists.add(new Category("蛋挞", 1));
        mRecyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mScheduleRecyclerAdapter = new ScheduleRecyclerAdapter(this, lists);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mScheduleRecyclerAdapter.getItemViewType(position)) {
                    case Category.SECOND_TYPE:
                        return 7;
                    case Category.THIRD_TYPE:
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView2.setLayoutManager(gridLayoutManager);
        mRecyclerView2.setAdapter(mScheduleRecyclerAdapter);
        mRecyclerView2.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_margin),
                getResources().getDimensionPixelSize(R.dimen.item_margin), getResources().getDimensionPixelSize(R.dimen.item_margin),
                getResources().getDimensionPixelSize(R.dimen.item_margin)));


        RecyclerView mRecyclerView3 = (RecyclerView) findViewById(R.id.recycler_view3);
        List<String> mContent= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mContent.add("当前的内容是：" + i);
        }
        mRecyclerView3.setLayoutManager(new GridLayoutManager(this, 2));
        ContentAdapter adapter = new ContentAdapter(this, mContent);
        HeaderAndFooterWrapper mWrapper = new HeaderAndFooterWrapper(adapter);
        TextView tvHead1 = new TextView(this);
        tvHead1.setText("当前是头布局1111");
        tvHead1.setGravity(Gravity.CENTER);
        TextView tvHead2 = new TextView(this);
        tvHead2.setText("当前是头布局222");
        tvHead2.setGravity(Gravity.CENTER);
        TextView tvFoot1 = new TextView(this);
        tvFoot1.setText("当前是尾布局1111");
        tvFoot1.setGravity(Gravity.CENTER);
        mWrapper.addHeaderView(tvHead1);
        mWrapper.addHeaderView(tvHead2);
        mWrapper.addFooterView(tvFoot1);
        mRecyclerView3.setAdapter(mWrapper);



        RecyclerView mRecyclerView4 = (RecyclerView) findViewById(R.id.recycler_view4);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView4.setLayoutManager(linearLayoutManager);
        initImages();
        ImageAdapter imageAdapter = new ImageAdapter(this, mDatas);
        mRecyclerView4.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }

    private void initImages() {
        mDatas.clear();
        List<ImageBean> bean1 = new ArrayList<>();
        List<ImageBean> bean2 = new ArrayList<>();
        List<ImageBean> bean3 = new ArrayList<>();
        // 每个子列表长度可以不相同
        bean1.add(new ImageBean("Dog", R.drawable.dog));
        bean1.add(new ImageBean("Dog", R.drawable.dog));
        bean2.add(new ImageBean("Cat", R.drawable.cat));
        bean3.add(new ImageBean("Bird", R.drawable.bird));

        mDatas.add(new RecyclerViewData("Dog", bean1, true));
        mDatas.add(new RecyclerViewData("Cat", bean2, true));
        mDatas.add(new RecyclerViewData("Bird", bean3, true));
    }
}