package com.example.common_base.base;


import android.widget.Toast;

public abstract class BaseMvpActivity <M> extends BaseActivity implements ICommonView {

    public CommonPresenter mPresenter;
    public M mModel;



    @Override
    public void initmvp() {
        mPresenter = getPresenter();
        mModel = getModel();
        if (mPresenter != null) mPresenter.attach(this, (ICommonModel) mModel);
    }

    public abstract int getLayoutId();


    public CommonPresenter getPresenter(){
        return PresenterFactoryProxy.createProxy(CommonPresenter.class,new CommonPresenter());
    }

    @Override
    public void onResponse(int whichApi, Object[] t) {
        mLoading.dismiss();
    }

    public abstract M getModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public void netErrorToast(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
