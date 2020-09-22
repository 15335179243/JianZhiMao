package com.chumu.jianzhimao.ui.activity;


import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;


import com.chumu.dt.v24.magicbox.ChuMuFramgentManager;
import com.chumu.jianzhimao.R;

import com.chumu.jianzhimao.ui.fragment.HomeFragment;
import com.chumu.jianzhimao.ui.fragment.MyFragment;
import com.chumu.jianzhimao.ui.fragment.RenMengFragment;
import com.chumu.jianzhimao.ui.fragment.XiHuanFragment;
import com.example.common_base.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    @BindView(R.id.container)
    FrameLayout mContainer;

    @Override
    protected int onCreateContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        getTitleView().mBackBtn.setVisibility(View.GONE);
        ChuMuFramgentManager.addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.container);
        getTitleView().setTitle("首页");
        getTitleView().setBackgroundColor(getResources().getColor(R.color.app_theme_color));
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    default:
                        break;
                    case R.id.navigation_home:
                        ChuMuFramgentManager.addFragment(getSupportFragmentManager(), HomeFragment.class, R.id.container);
                        getTitleView().setTitle("首页");
                        break;
                    case R.id.navigation_dashboard:
                        ChuMuFramgentManager.addFragment(getSupportFragmentManager(), RenMengFragment.class, R.id.container);
                        getTitleView().setTitle("热门");
                        break;
                    case R.id.navigation_notifications:
                        ChuMuFramgentManager.addFragment(getSupportFragmentManager(), XiHuanFragment.class, R.id.container);
                        getTitleView().setTitle("收藏");
                        break;
                    case R.id.navigation_my:
                        ChuMuFramgentManager.addFragment(getSupportFragmentManager(), MyFragment.class, R.id.container);
                        getTitleView().setTitle("我的");
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.navigation})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.navigation:
                break;
        }
    }


}
