package com.example.common_base.base;


import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chumu.dt.v24.magicbox.ChuMuSharedPreferences;
import com.chumu.dt.v24.magicbox.base.ChuMuBaseActivity;
import com.example.common_base.R;
import com.example.common_base.SPConstant;
import com.example.common_base.boradcast.NetStatusBroadCast;
import com.example.common_base.design.CommonTitle;
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

public abstract class BaseActivity extends ChuMuBaseActivity implements NetStatusBroadCast.NetStatusListener, NotSingOnEvent {
    private LinearLayoutManager mManager;
    public BaseApplication mApplication;
    private Unbinder mBind;
    public NetStatusBroadCast mNetStatusBroadCast;
    public LoadingDialogWithContent mLoading;
    public ChuMuSharedPreferences mChuMuSharedPreferences;
    private View mBaseLayout;

    private View mContentView;
    private CommonTitle mTitleView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseLayout = getLayout();
        if (mBaseLayout != null) {
            mChuMuSharedPreferences = new ChuMuSharedPreferences(this, SPConstant.PORTRAIT_NAME);
            mLoading = new LoadingDialogWithContent(this, "正在加载");
            showLog("我就是一个类：" + this.getClass().getSimpleName());
            mApplication = (BaseApplication) getApplication();
            setContentView(mBaseLayout);
            FrameLayout titleContainer = mBaseLayout.findViewById(R.id.chumu_base_title);
            int titleLayoutId = onCreateTitleView();
            if (titleLayoutId > 0) {
                View mTitleLayout = LayoutInflater.from(this).inflate(titleLayoutId, titleContainer, true);
                initTitle(mTitleLayout);
            }

            FrameLayout contentContainer = mBaseLayout.findViewById(R.id.chumu_base_content);
            int contentLayoutId = onCreateContentView();
            if (contentLayoutId > 0) {
                mContentView = LayoutInflater.from(this).inflate(contentLayoutId, contentContainer, true);
            } else throw new NullPointerException("baseContentLayout View is null !!");
            ExitApplication.getInstance().addActivity(this);
            mBind = ButterKnife.bind(this);
            GlobalCrashCapture.onNotSignEvent(this);
            initmvp();
            initView();
            initData();
        } else {
            throw new NullPointerException("baseLayout View is null !!");
        }


    }

    private void initTitle(View mTitleLayout) {
        if (mTitleLayout == null) throw new NullPointerException("baseTitleLayout View is null !!");
        mTitleView = mTitleLayout.findViewById(R.id.chumu_base_title_view);
        mTitleView.setTitle(getTitle().toString());
        mTitleView.setCommonTitleListener(new CommonTitle.onCommonTitleListener() {
            @Override
            public void onTitleClick(View view) {
                onTitleViewTitleClick(view);
            }

            @Override
            public void onMoreClick(View view) {
                onTitleViewMoreClick(view);
            }
        });
    }

    protected void onTitleViewTitleClick(View view) {
    }

    protected void onTitleViewMoreClick(View view) {
    }

    /**
     * 设置内容布局
     *
     * @return
     */
    protected abstract @LayoutRes
    int onCreateContentView();


    public View getContentView() {
        return mContentView;
    }

    public CommonTitle getTitleView() {
        return mTitleView;
    }

    public void initmvp() {

    }

    /**
     * 设置标题布局
     *
     * @return
     */
    protected @LayoutRes
    int onCreateTitleView() {
        return R.layout.chumu_base_title_layout;
    }

    public void show() {

        mLoading.show();
    }

    public void hide() {
        mLoading.dismiss();
    }

    private View getLayout() {
        return LayoutInflater.from(this).inflate(R.layout.chumu_fram_base_layout, null, false);
    }

    public abstract void initView();

    public abstract void initData();

    public void showLog(String content) {
        Log.e(NormalConfig.log1, content);
    }

    public void showLog(boolean content) {
        Log.e(NormalConfig.log1, "" + content);
    }

    public void showToast(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    public void initRecycleView(RecyclerView recyclerView, RefreshLayout refreshLayout) {
        mManager = new LinearLayoutManager(this);
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

    public void registerNetWorkStatus() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetStatusBroadCast = new NetStatusBroadCast();
        mNetStatusBroadCast.setNetStatusListener(this);
        registerReceiver(mNetStatusBroadCast, filter);
    }

    @Override
    public void onNetChanged(int state) {
        if (state == NETWORK_MOBILE || state == NETWORK_WIFI) onNetConnected();
        else if (state == NETWORK_NONE) onNetDisConnected();
    }

    //没有网络时候回调
    public void onNetDisConnected() {
    }

    //有网络时候回调
    public void onNetConnected() {

    }

    protected int getLoadType(Object[] t) {
        return (int) t[1];
    }

    public void refresh() {

    }

    public void loadMore() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNetStatusBroadCast != null) unregisterReceiver(mNetStatusBroadCast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetWorkStatus();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoading = null;
        mBind.unbind();
        LeakCanaryUtils.fixFocusedViewLeak(getApplication());
    }

    @Override
    public void onSingEvent() {
        //未登录操作
    }

    /**
     * 全屏 且隐藏标题栏
     * （子类需要直接使用）
     */
    public void setNoTitleBarAndFullScreen() {
        // requestWindowFeature(Window.FEATURE_NO_TITLE); 此句必须在setContent之前
//        getSupportActionBar().hide();// 标题栏的隐藏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //全屏处理
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 沉浸式状态栏
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setImmersionStatusBar() {
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

}
