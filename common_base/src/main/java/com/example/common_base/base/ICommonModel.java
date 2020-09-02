package com.example.common_base.base;

public interface ICommonModel<T> {
    void getData(ICommonView view, int whichApi, T... t);
}
