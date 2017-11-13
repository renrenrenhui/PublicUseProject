package com.example.xiaoniu.publicuseproject.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;


public abstract class BaseFragment extends Fragment {

    protected FragmentActivity activity;

    private int mLayoutId;

    private boolean isFirstVisible = true;
    private boolean isVisible = false;
    private boolean isReuseView = false;
    private View rootView = null;
    private View content;
    private FrameLayout flErrorPanel;
    private ViewStub errorStub;
    private View errorLayout;
    private ImageView ivTipImg;
    private TextView tvRefresh, tvTipText;

    // open lazy load when this value is true or override the super
    private boolean isLazyLoadAvalible = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FragmentActivity)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVisibleVar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_framgent_layout, container, false);
        LinearLayout llContainer = (LinearLayout) view.findViewById(R.id.base_container);
        llContainer.removeAllViews();

        flErrorPanel = (FrameLayout) inflater.inflate(R.layout.net_error_panel, null, false);
        LinearLayout.LayoutParams panelParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flErrorPanel.setLayoutParams(panelParams);
        errorStub = (ViewStub) flErrorPanel.findViewById(R.id.vs_net_error);

        mLayoutId = initLayout();
        if (mLayoutId != 0){
            // add content layout to base layout
            content = inflater.inflate(mLayoutId, container, false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            content.setLayoutParams(params);
            flErrorPanel.addView(content);
            llContainer.addView(flErrorPanel);
        }

        initViews(view);
        initResource();

        return view;
    }

    /**
     * Called immediately after onCreateView
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }else{
                    onFragmentVisibleChanged(true);
                    isVisible = true;
                }
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    // reload view when the view is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstVisible = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVisibleVar();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // avoid to do ui operation when ui is null
        if (rootView == null)
            return;
        if (isFirstVisible && isVisibleToUser){
            onFragmentFirstVisible();
            isFirstVisible = false;
            // only can handle one of onFragmentVisibleChanged and onFragmentFirstVisible
            // avoid loading data and ui twice
            return;
        }
        if (isVisibleToUser){
            isVisible = true;
        }else{
            isVisible = false;
        }
        onFragmentVisibleChanged(isVisible);
    }

    /**
     * Initialize the visible vars
     */
    private void initVisibleVar(){
        isFirstVisible = true;
        isVisible = false;
        isReuseView = false;
        rootView = null;
    }

    private void initExceptionUI(){
        errorLayout = errorStub.inflate();
        tvRefresh = (TextView) errorLayout.findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNetUnable(true);
                refreshData();
            }
        });
        ivTipImg = (ImageView) errorLayout.findViewById(R.id.iv_tip_img);
        tvTipText = (TextView) errorLayout.findViewById(R.id.tv_tip_text);
    }

    // show the content ui by unable
    protected void setNetUnable(boolean unable){
        if (unable){
            if (errorLayout != null)
                errorLayout.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }else{
            if (errorLayout == null){
                initExceptionUI();
            }
            errorLayout.setVisibility(View.VISIBLE);
            content.setVisibility(View.GONE);
        }
    }

    protected void setDataUnable(boolean unable){
        if (!unable){
            ivTipImg.setVisibility(View.GONE);
            tvTipText.setVisibility(View.VISIBLE);
        }
    }

    protected void setRefreshUnable(boolean unable){
        if (unable){
            tvRefresh.setVisibility(View.VISIBLE);
        }else{
            tvRefresh.setVisibility(View.GONE);
        }
    }

    protected void refreshData(){
    }

    /**
     * Determine whether the fragment is visible or not
     * @return true or false
     */
    protected boolean isFragmentVisible(){
        return isVisible;
    }

    protected void onFragmentVisibleChanged(boolean visible){
        if (!isLazyLoadAvalible)
            return;
    }

    protected void onFragmentFirstVisible(){
        if (!isLazyLoadAvalible)
            return;
    }

    protected abstract int initLayout();

    /**
     * get data from intent or uri
     */
    protected abstract void initResource();

    /**
     * init all views
     * @param view convert view
     */
    protected abstract void initViews(View view);

    public abstract String getFragmentTag();

    /**
     * Determine whether the network is connected
     * @param context
     * @return
     */
    public static boolean isNetWorkActive(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }

        return false;
    }
}
