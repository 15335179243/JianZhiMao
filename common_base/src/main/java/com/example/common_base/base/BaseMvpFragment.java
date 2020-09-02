package com.example.common_base.base;


import android.widget.Toast;

public abstract class BaseMvpFragment<M> extends BaseFramgent implements ICommonView {

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


    public abstract M getModel();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detach();
    }


    public void netErrorToast(Throwable e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
