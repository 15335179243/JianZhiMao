package com.example.common_base.utils;

import com.example.common_base.BuildConfig;

public class DebugToast {

    public static void showShort(String message) {
        if (BuildConfig.DEBUG) {
            ToastUtil.toastShortMessage(message);
        }
    }

}