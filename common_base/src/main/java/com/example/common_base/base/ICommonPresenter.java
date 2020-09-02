package com.example.common_base.base;

public interface ICommonPresenter<T> {
    void getData(int whichApi, T... t);
}
