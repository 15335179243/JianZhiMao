package com.example.common_base.base;

import android.text.TextUtils;


import com.example.common_base.ApiService;
import com.example.common_base.base.convert.MyGsonConverterFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by ChuMu on 2019/6/27.
 */
public class NetManager {
    private static volatile NetManager sNetManager;

    private NetManager() {
    }

    public static NetManager getNetManager() {
        if (sNetManager == null) {//考虑效率问题
            synchronized (NetManager.class) {
                if (sNetManager == null) {//考虑多个线程问题
                    sNetManager = new NetManager();
                }
            }
        }
        return sNetManager;
    }

    public <T> ApiService getNetService(T... t) {
        ApiService service = new Retrofit.Builder()
                .baseUrl(t != null && t.length != 0 && !TextUtils.isEmpty((String) t[0]) ? (String) t[0] : NetConfig.BaseUrl)
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(NetInterceptor.getNetInterceptor().getClientWithoutCache())
                .build().create(ApiService.class);


        return service;
    }


    public <T> void method(Observable<T> leftMenuInfo, final ICommonView view, final int whichApi, final Object... t){
        leftMenuInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onSuccess(Object value) {
                        view.onResponse(whichApi, value,t);
                    }


                    @Override
                    public void onFailed(Throwable e) {
                        e.addSuppressed(e);
                        view.onError(whichApi, e);
                    }
                });

    }
}
