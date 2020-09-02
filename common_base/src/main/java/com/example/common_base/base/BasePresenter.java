package com.example.common_base.base;

import android.view.View;

import com.example.common_base.utils.DebugToast;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BasePresenter<V extends ICommonView, M extends ICommonModel>{
    private WeakReference<V> view;
    private WeakReference<M> model;

    public void attach(V view, M model) {

        this.view =  new WeakReference<>((V) new ProxyICommonViewInvocationHandler(view).getProxy());
        this.model = new WeakReference<>(model);
    }

    public void detach() {
        if (view != null) {
            view.clear();
            this.view = null;
        }
        if (model != null) {
            model.clear();
            this.model = null;
        }
    }

    public V getView() {
        return view != null ? view.get() : null;
    }

    public M getModel() {
        return model != null ? model.get() : null;
    }

    static class ProxyICommonViewInvocationHandler implements InvocationHandler {

        Object mTarget;

        ProxyICommonViewInvocationHandler(Object target){
            this.mTarget = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            Object result = null;
            try {
                if(mTarget!=null){
                    result = method.invoke(mTarget, args);
                }
            }catch (Throwable e){
                DebugToast.showShort(mTarget.getClass().getSimpleName()+"中发生异常");
                e.printStackTrace();
//                BlackBox.postException(e);

            }
            return result;
        }

        //generator proxy object
        Object getProxy() {
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Class<?>[] interfaces = mTarget.getClass().getInterfaces();
                return Proxy.newProxyInstance(loader, interfaces, this);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }

}
