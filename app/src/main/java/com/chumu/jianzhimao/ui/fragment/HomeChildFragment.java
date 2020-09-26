package com.chumu.jianzhimao.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chumu.jianzhimao.R;
import com.chumu.jianzhimao.ui.activity.webview.WebActivity;
import com.chumu.jianzhimao.ui.adapter.HomeChildAdapter;
import com.chumu.jianzhimao.ui.mvp.HomeModle;
import com.chumu.jianzhimao.ui.mvp.bean.BeanBannerList;
import com.chumu.jianzhimao.ui.mvp.bean.BeanHomeList;
import com.chumu.jianzhimao.ui.mvp.bean.HomeChildInfo;
import com.example.common_base.ApiConfig;
import com.example.common_base.AppConfig;
import com.example.common_base.base.BaseMvpFragment;
import com.example.common_base.utils.LoadStatusConfig;
import com.example.common_base.utils.ToastUtil;
import com.google.gson.Gson;
import com.luck.picture.lib.tools.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

import static com.chumu.jianzhimao.ui.mvp.bean.HomeChildInfo.PAGE_TYPE_BANNER;
import static com.chumu.jianzhimao.ui.mvp.bean.HomeChildInfo.PAGE_TYPE_LIST;
import static com.example.common_base.ApiConfig.APP_LIST;
import static com.example.common_base.ApiConfig.GET_BANNER_LIST;

/**
 * JianZhiMao
 * <p>
 * Created by ChuMu on 2020/9/6
 * Copyright © 2020年 ChuMu. All rights reserved.
 * <p>
 * Describe:
 */

public class HomeChildFragment extends BaseMvpFragment<HomeModle> {

    @BindView(R.id.list_recyclerview)
    RecyclerView mListRecyclerview;
    @BindView(R.id.sm_refres)
    SmartRefreshLayout mSmRefres;
    int page = 0;
    private int mPlace;
    private BeanHomeList mBeanHomeList;
    private BeanBannerList mBeanBannerList;
    private HomeChildAdapter mHomeChildAdapter;
    private ArrayList<HomeChildInfo> mHomeChildInfo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_child;
    }

    @Override
    public void initView() {
        mPlace = getArguments().getInt(AppConfig.DataTag.PLACE, -1);
        initRecycleView(mListRecyclerview, mSmRefres);
        mHomeChildAdapter = new HomeChildAdapter(this);
        mListRecyclerview.setAdapter(mHomeChildAdapter);
        mHomeChildInfo = new ArrayList<>();
        mHomeChildAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    switch (view.getId()) {
                        default:
                            break;
                        case R.id.img_shoucang:
                            ImageView favorite = view.findViewById(R.id.img_shoucang);
                            favorite.setSelected(!mHomeChildInfo.get(position).getHomeData().isHaveCollect());
                            mHomeChildInfo.get(position).getHomeData().setHaveCollect(!mHomeChildInfo.get(position).getHomeData().isHaveCollect());
                            mPresenter.getData(ApiConfig.GET_STATISTICS_NUM,mHomeChildInfo.get(position).getHomeData().getId(),AppConfig.DataTag.favorite,mHomeChildInfo.get(position).getHomeData().isHaveCollect()? AppConfig.DataTag.inFavorite:AppConfig.DataTag.unFavorite);
                            show();
                            break;
                        case R.id.img_fenxiang:
                            ToastUtil.toastShortMessage("分享");
                            break;
                        case R.id.item_cl:
                            getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("webUrl",mHomeChildInfo.get(position).getHomeData().getPositionDetailsLink()));
                            ToastUtil.toastShortMessage("详情");
                            break;


                    }
                }

        });
    }

    @Override
    public void initData() {
        mPresenter.getData(APP_LIST, page, 20, mPlace, LoadStatusConfig.NORMAL_LOAD);
        if (mPlace == 1) mPresenter.getData(GET_BANNER_LIST, LoadStatusConfig.NORMAL_LOAD);
    }

    @Override
    public void refresh() {
        super.refresh();
        ToastUtil.toastShortMessage("1");
        page = 0;
        mPresenter.getData(APP_LIST, page, 20, mPlace, LoadStatusConfig.REFRESH_LOAD);
        if (mPlace == 1) mPresenter.getData(GET_BANNER_LIST, LoadStatusConfig.REFRESH_LOAD);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        ToastUtil.toastShortMessage("2");
        page++;
        mPresenter.getData(APP_LIST, page, 20, mPlace, LoadStatusConfig.MORE_LOAD);
    }

    @Override
    public HomeModle getModel() {
        return new HomeModle();
    }

    @Override
    public void onError(int whichApi, Throwable e) {

    }

    @Override
    public void onResponse(int whichApi, Object[] t) {
        hide();
        String str = (String) t[0];
        switch (whichApi) {
            default:
                break;
            case APP_LIST:
                mBeanHomeList = new Gson().fromJson(str, BeanHomeList.class);

                if (getLoadType(t) == LoadStatusConfig.REFRESH_LOAD) {
                    mHomeChildInfo.clear();
                    mSmRefres.finishRefresh();

                } else if (getLoadType(t) == LoadStatusConfig.MORE_LOAD) mSmRefres.finishLoadMore();
                if (mBeanHomeList.getCode() == 200) {

                } else {
                    ToastUtil.toastShortMessage(mBeanHomeList.getDesc());
                }
                break;
            case GET_BANNER_LIST:
                mBeanBannerList = new Gson().fromJson(str, BeanBannerList.class);
                if (mBeanBannerList.getCode() == 200) {

                } else {
                    ToastUtil.toastShortMessage(mBeanBannerList.getDesc());
                }
                break;


        }
        if (whichApi==GET_BANNER_LIST||whichApi==APP_LIST) {
            if (mPlace == 1 && page == 0 && mBeanHomeList != null && mBeanHomeList.getData() != null
                    && mBeanHomeList.getData().getRows() != null
                    && mBeanHomeList.getData().getRows().size() > 0
                    && mBeanBannerList.getData() != null
                    && mBeanBannerList.getData().size() > 0) {
                mHomeChildInfo.add(new HomeChildInfo(PAGE_TYPE_BANNER, mBeanBannerList.getData()));
                for (BeanHomeList.DataBean.RowsBean row : mBeanHomeList.getData().getRows()) {
                    mHomeChildInfo.add(new HomeChildInfo(PAGE_TYPE_LIST, row));
                }

            } else if (mBeanHomeList != null && mBeanHomeList.getData() != null
                    && mBeanHomeList.getData().getRows() != null) {
                for (BeanHomeList.DataBean.RowsBean row : mBeanHomeList.getData().getRows()) {
                    mHomeChildInfo.add(new HomeChildInfo(PAGE_TYPE_LIST, row));
                }
            }
            mHomeChildAdapter.setNewData(mHomeChildInfo);
            mHomeChildAdapter.notifyDataSetChanged();
        }

    }
}