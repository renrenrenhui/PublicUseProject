package com.example.xiaoniu.publicuseproject.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;

import java.util.ArrayList;
import java.util.List;


// ① 创建Adapter
public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Category> lists = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ScheduleRecyclerAdapter(Context context, List<Category> lists) {
        this.context = context;
        this.lists = lists;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        return lists.get(position).getType();
    }

    /**
     * 根据不同类型绑定不同布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Category.SECOND_TYPE) {
            return new SecondViewHolder(layoutInflater.inflate(R.layout.item_list_category_one, null, false));
        } else {
            return new ThirdViewHolder(layoutInflater.inflate(R.layout.item_list_category_two, null, false));
        }
    }

    /**
     * 根据不同类型布局，绑定不同数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Category.SECOND_TYPE:
                SecondViewHolder secondViewHolder = (SecondViewHolder) holder;
                secondViewHolder.tvOneCategory.setText(lists.get(position).getCategoryName());
                break;
            case Category.THIRD_TYPE:
                ThirdViewHolder thirdViewHolder = (ThirdViewHolder) holder;
                thirdViewHolder.tvTwoCategory.setText(lists.get(position).getCategoryName());
                break;

        }
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        } else {
            return 0;
        }
    }

    /**
     * 第一种ViewHolder
     */
    public class SecondViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOneCategory;

        public SecondViewHolder(View itemView) {
            super(itemView);
            tvOneCategory = (TextView) itemView.findViewById(R.id.tv_one_cat_name);
        }
    }

    /**
     * 第二种ViewHolder
     */
    public class ThirdViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTwoCategory;

        public ThirdViewHolder(View itemView) {
            super(itemView);
            tvTwoCategory = (TextView) itemView.findViewById(R.id.tv_two_cat_name);
        }
    }
}