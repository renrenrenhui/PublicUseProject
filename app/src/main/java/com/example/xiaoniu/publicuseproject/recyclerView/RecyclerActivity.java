package com.example.xiaoniu.publicuseproject.recyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView1;
    private UnitRecyclerAdapter mUnitRecyclerAdapter;
    private List<String> mUnitList = new ArrayList<>();
    private RecyclerView mRecyclerView2;
    private ScheduleRecyclerAdapter mScheduleRecyclerAdapter;
    List<Category> lists = new ArrayList<>();

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
    }
}