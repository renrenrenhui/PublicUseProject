package com.example.xiaoniu.publicuseproject.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;

import java.util.List;


// ① 创建Adapter
public class UnitRecyclerAdapter extends RecyclerView.Adapter<UnitRecyclerAdapter.VH> {

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder {
        public final TextView text;
        public final ImageView image;

        public VH(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.tv_card_text);
            image = (ImageView) v.findViewById(R.id.iv_card_img);
        }
    }

    // 定义点击回调接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // 定义一个设置点击监听器的方法
    public void setOnItemClickListener(UnitRecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private Context mContext;
    private List<String> mDatas;
    // 事件回调监听
    private UnitRecyclerAdapter.OnItemClickListener onItemClickListener;

    public UnitRecyclerAdapter(Context context, List<String> data) {
        this.mDatas = data;
        this.mContext = context;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.text.setText(mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_unit, parent, false);
        return new VH(view);
    }
}