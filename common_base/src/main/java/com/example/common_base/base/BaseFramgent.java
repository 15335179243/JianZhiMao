package com.example.common_base.base;


import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common_base.boradcast.NetStatusBroadCast;
import com.example.common_base.design.LoadingDialogWithContent;
import com.example.common_base.utils.NormalConfig;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.common_base.local_utils.NetworkUtils.NETWORK_MOBILE;
import static com.example.common_base.local_utils.NetworkUtils.NETWORK_NONE;
import static com.example.common_base.local_utils.NetworkUtils.NETWORK_WIFI;
import static com.scwang.smartrefresh.layout.util.SmartUtil.px2dp;


public abstract class BaseFramgent extends Fragment implements NetStatusBroadCast.NetStatusListener{
    public LinearLayoutManager mManager;
    public BaseApplication mApplication;
    private Unbinder mBind;
    public NetStatusBroadCast mNetStatusBroadCast;
    private LoadingDialogWithContent mLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLoading = new LoadingDialogWithContent(getContext(), "正在加载...");
        showLog("我就是一个类："+this.getClass().getSimpleName());
        mApplication = (BaseApplication) getActivity().getApplication();
        View inflate = inflater.inflate(getLayoutId() ,container, false);
        mBind = ButterKnife.bind(this, inflate);
        initmvp();
        initView();
        initData();
        return inflate;
    }

    public  void initmvp(){};

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();
    public void showLog(String content) {
        Log.e(NormalConfig.log1, content);
    }

    public void showLog(boolean content) {
        Log.e(NormalConfig.log1, "" + content);
    }

    public void initRecycleView(RecyclerView recyclerView, RefreshLayout refreshLayout) {
        mManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mManager);
        if (refreshLayout != null) {
            refreshLayout.setHeaderHeight(px2dp(120));
            refreshLayout.setFooterHeight(px2dp(100));
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refresh();
                }
            });
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    loadMore();
                }
            });
        }
    }

    protected int getLoadType( Object... t){
        return  (int) ((Object[]) t[1])[1];
    }

    public void refresh() {
    }

    public void loadMore() {
    }
    public void registerNetWorkStatus() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetStatusBroadCast = new NetStatusBroadCast();
        mNetStatusBroadCast.setNetStatusListener(this);
       getContext().registerReceiver(mNetStatusBroadCast, filter);
    }

    private static final String TAG = "BaseFramgent";

    @Override
    public void onNetChanged(int state) {
        if (state == NETWORK_MOBILE || state == NETWORK_WIFI) onNetConnected();
        else if (state == NETWORK_NONE) onNetDisConnected();
    }
    //没有网络时候回调
    public void onNetDisConnected() {
        Log.d(TAG, "onNetDisConnected: "+1);
    }
    //有网络时候回调
    public void onNetConnected() {
        Log.d(TAG, "onNetDisConnected: "+2);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mNetStatusBroadCast != null) getActivity().unregisterReceiver(mNetStatusBroadCast);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerNetWorkStatus();
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    public void show() {

            mLoading.show();

    }

    public void hide() {

            mLoading.dismiss();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoading=null;
        mBind.unbind();
    }
}
