package com.example.xiaoniu.publicuseproject.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private Context context;
    private List<String> mContent;

    public ContentAdapter(Context context, List<String> mContent) {
        this.context = context;
        this.mContent = mContent;
    }

    @Override
    public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(View.inflate(context, R.layout.item_recycler_header, null));
    }

    @Override
    public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {
        holder.tvInfo.setText(mContent.get(position) + "--adapter的position:" + position);
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    class ContentHolder extends RecyclerView.ViewHolder{

        public TextView tvInfo;

        public ContentHolder(View itemView) {
            super(itemView);
            tvInfo = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}