package com.example.common_base.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * ChuMuProjectFramework
 * <p>
 * Created by ChuMu on 2020/5/22
 * Copyright © 2020年 ChuMu. All rights reserved.
 * <p>
 * Describe: 关闭多个页面
 */

public class ExitApplication extends BaseApplication {

    private List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;

    private ExitApplication() {
    }

    //单例模式中获取唯一的ExitApplication实例
    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;

    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    //遍历所有Activity并finish

    public void exit() {

        for (Activity ac : activityList) {
            ac.finish();
        }

        System.exit(0);

    }
}