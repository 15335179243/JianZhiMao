package com.example.common_base.utils;

import android.os.Handler;


public class BackgroundTasks {

    private Handler mHandler = new Handler();


    public void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public boolean postDelayed(Runnable r, long delayMillis) {
        return mHandler.postDelayed(r, delayMillis);
    }

    public Handler getHandler() {
        return mHandler;
    }

    private static BackgroundTasks instance;

    public static BackgroundTasks getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return instance = new BackgroundTasks();
        }

    }


}
