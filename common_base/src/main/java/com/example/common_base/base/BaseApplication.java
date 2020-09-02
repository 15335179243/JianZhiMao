package com.example.common_base.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.chumu.dt.v24.magicbox.appbox.ChuMuAppUtils;
import com.example.common_base.BuildConfig;
import com.example.common_base.local_utils.DeviceUuidFactory;

import java.util.UUID;

import leakcanary.LeakCanary;

public class BaseApplication extends Application {
    public static BaseApplication sApplication;
    public static UUID mUuid;
    private static String tAppCacheDir;

    public static String getAppCacheDir() {
        return tAppCacheDir;
    }

    public static void setAppCacheDir( String appCacheDir) {
       tAppCacheDir=appCacheDir;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        mUuid = DeviceUuidFactory.getInstance(getApplicationContext()).getDeviceUuid();
        Log.e("uuid:", mUuid + "\n" + mUuid.toString());
        GlobalCrashCapture.instance().init(this, BuildConfig.DEBUG);
        ChuMuAppUtils.init(this);
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }

    public static BaseApplication getApplication() {
        return sApplication;
    }

}
