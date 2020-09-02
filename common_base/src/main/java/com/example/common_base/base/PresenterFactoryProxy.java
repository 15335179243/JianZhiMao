package com.example.common_base.base;

import android.util.Log;

import com.example.common_base.utils.DebugToast;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**

 * Description: Presenter代理
 */
public class PresenterFactoryProxy implements InvocationHandler {

    private Object mTarget;

    private String TAG = this.getClass().getSimpleName();

    private PresenterFactoryProxy(Object target){
        this.mTarget = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        try{
            Log.d(TAG, "PresenterFactoryProxy : invoke method = " + method.getName());
            result = method.invoke(mTarget, args);
        }catch (Throwable e){
            e.printStackTrace();
            DebugToast.showShort("Presenter实现类发生异常");
//            BlackBox.postException(e);
        }
        return result;
    }

    //generator proxy object
    public static <T> T createProxy(Class<T> anInterface, Object target){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = new Class[]{anInterface};
        return (T) Proxy.newProxyInstance(loader, interfaces, new PresenterFactoryProxy(target));
    }

    public static <T> T createProxy(Class<T> anInterface, Class<? extends T> instanceCls){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = new Class[]{anInterface};
        T newInstance = null;
        try {
            newInstance = instanceCls.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) Proxy.newProxyInstance(loader, interfaces, new PresenterFactoryProxy(newInstance)) ;
    }

}
