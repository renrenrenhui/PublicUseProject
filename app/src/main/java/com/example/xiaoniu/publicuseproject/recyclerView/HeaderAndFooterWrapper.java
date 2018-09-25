package com.example.xiaoniu.publicuseproject.recyclerView;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }

    /**
     * 是否为头布局
     * @param position
     * @return
     */
    private boolean isHeaderView(int position){
        return position < getHeadersCount();
    }

    /**
     * 是否为尾布局
     * @param position
     * @return
     */
    private boolean isFooterView(int position){
        return position >= getHeadersCount() + getRealItemCount();
    }

    /**
     * 添加头布局
     * @param view
     */
    public void addHeaderView(View view){
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    /**
     * 添加尾布局
     * @param view
     */
    public void addFooterView(View view){
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    /**
     * 头布局的数量
     * @return
     */
    private int getHeadersCount(){
        return mHeaderViews.size();
    }

    /**
     * 尾布局的数量
     * @return
     */
    public int getFootersCount(){
        return mFootViews.size();
    }

    /**
     * item的数量
     * @return
     */
    private int getRealItemCount(){
        return mInnerAdapter.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderViews.get(viewType) != null){
            RecyclerView.ViewHolder holder = new HeaderHolder(mHeaderViews.get(viewType));
            return holder;
        }

        if(mFootViews.get(viewType) != null){
            RecyclerView.ViewHolder holder = new FooterHolder(mFootViews.get(viewType));
            return holder;
        }

        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isHeaderView(position)){
            return ;
        }
        if(isFooterView(position)){
            return;
        }

        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemViewType(int position) {
        if(isHeaderView(position)){
            return mHeaderViews.keyAt(position);
        }else if(isFooterView(position)){
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }else{
            return mInnerAdapter.getItemViewType(position - getHeadersCount());
        }
    }

    /**
     * 条目的总数量
     * @return
     */
    @Override
    public int getItemCount() {
        return getHeadersCount() + getRealItemCount() + getFootersCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

                @Override
                public int getSpanSize(int position) {

                    int viewType = getItemViewType(position);
                    if(mHeaderViews.get(viewType) != null || mFootViews.get(viewType) != null){
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder){
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderView(position) || isFooterView(position))
        {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams)
            {

                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder{

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder{

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

}