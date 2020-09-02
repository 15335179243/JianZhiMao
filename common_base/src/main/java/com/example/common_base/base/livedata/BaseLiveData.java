//package com.example.common_base.base.livedata;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Observer;
//
//import com.example.common_base.base.ExtData;
//
//import io.reactivex.internal.util.ExceptionHelper;
//
///**
// * ChuMuProjectFramework
// * <p>
// * Created by ChuMu on 2020/5/26
// * Copyright © 2020年 ChuMu. All rights reserved.
// * <p>
// * Describe: 观察者模式切换线程
// */
//
//public final class BaseLiveData<T> extends MutableLiveData<T>  {
//
//    /**
//     * 记录数据的状态
//     */
//    private MutableLiveData<State> mState = new MutableLiveData<>();
//
//    /**
//     * 数据判定模型
//     */
//    private DataJudgeModel<T> mDataJudgeModel;
//
//    /**
//     * Load组件结果类型判定
//     */
//    private LoadWidgetStateJudge<T> mLoadWidgetStateJudge;
//
//    /**
//     * 是否代理处理<<错误>>提示消息，默认处理
//     */
//    private boolean stateProxyErrorMessage = true;
//
//    /**
//     * 是否代理处理<<成功>>提示消息，默认不处理
//     */
//    private boolean stateProxySuccessMessage = false;
//
//    /**
//     * tag标记
//     */
//    private Object mTag;
//
//    public BaseLiveData(T value) {
//        super(value);
//    }
//
//    public BaseLiveData() {
//    }
//
//    public BaseLiveData<T> proxyErrorMessage(boolean stateProxyErrorMessage) {
//        this.stateProxyErrorMessage = stateProxyErrorMessage;
//        return this;
//    }
//
//    public BaseLiveData<T> proxySuccessMessage(boolean stateProxySuccessMessage) {
//        this.stateProxySuccessMessage = stateProxySuccessMessage;
//        return this;
//    }
//
//    public BaseLiveData<T> dataJudgeModel(DataJudgeModel<T> dataJudgeModel) {
//        this.mDataJudgeModel = dataJudgeModel;
//        return this;
//    }
//
//    public BaseLiveData<T> loadWidgetStateJudge(LoadWidgetStateJudge<T> loadWidgetStateJudge){
//        this.mLoadWidgetStateJudge = loadWidgetStateJudge;
//        return this;
//    }
//
//
//    public Object getTag() {
//        return mTag;
//    }
//
//
//    public StateDispatcher getStateDispatcher() {
//        return this;
//    }
//
//    /**
//     * Deprecated method, It is recommended to use this method {@link #observe(LifecycleOwner, Observer, Observer)}
//     * @param owner
//     * @param observer
//     */
//    @SuppressWarnings("unchecked")
//    @Override
//    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
//        super.observe(owner, new ProxyObserver<>(this, observer, mInternalOnDataChangeObserver));
//    }
//
//    /**
//     * 同时注册数据的观察者和状态的观察者，确保注册顺序（因为同一个LifecycleOwner中observer的回调顺序依照注册顺序）。
//     * @param owner
//     * @param observer
//     * @param stateObserver
//     */
//    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer, Observer<State> stateObserver){
//        if(stateObserver!=null)
//            mState.observe(owner, stateObserver);
//        observe(owner, observer);
//    }
//
//    public void postValue(T value, Object tag) {
//        this.mTag = tag;
//        super.postValue(value);
//    }
//
//    private OnDataChangeObserver mInternalOnDataChangeObserver = new OnDataChangeObserver() {
//        @Override
//        public void onDataChange() {
//            boolean nullData = mDataJudgeModel!=null && mDataJudgeModel.nullData(getValue());
//            if(nullData){
//                postStateValue(new State(State.STATE_RESULT_NULL_DATA));
//            }
//            if(mLoadWidgetStateJudge !=null){
//                mLoadWidgetStateJudge.onResult(getValue(), nullData);
//            }
//        }
//    };
//
//    /**
//     * 分发状态值
//     * @param state
//     */
//
//    public void postStateValue(State state){
//        mState.postValue(state);
//        onStateDispatch(state);
//        if(mLoadWidgetStateJudge !=null)
//            mLoadWidgetStateJudge.onStateChange(state);
//    }
//
//    private void onStateDispatch(State state){
//        switch (state.state){
//            case State.STATE_ERROR:
//                if(isStateProxyErrorMessage()){
//                    NetworkTip.proxyError(state);
//                }
//                break;
//            case State.STATE_RESULT:
//                if(isStateProxySuccessMessage()){
//                    NetworkTip.proxySuccess(state);
//                }
//                break;
//        }
//    }
//
//    private boolean isStateProxyErrorMessage() {
//        return stateProxyErrorMessage;
//    }
//
//    private boolean isStateProxySuccessMessage() {
//        return stateProxySuccessMessage;
//    }
//
//    /**
//     * 如果是分发状态，建议调用{@link #postStateValue(State)}
//     * 如果是注册观察者，建议使用{@link #observe(LifecycleOwner, Observer, Observer)}
//     * 如果是获取当前State值，建议使用{@link #getStateValue()}
//     * @return
//     */
//    @Deprecated
//    public MutableLiveData<State> getState() {
//        return mState;
//    }
//
//    /**
//     * 获取当前的状态值
//     * @return
//     */
//    public State getStateValue(){
//        return mState.getValue();
//    }
//
//    /**
//     * observer代理类的包装类，用于保证{@link Observer#onChanged(Object)}和{@link DataJudgeModel#nullData(Object)}的执行顺序
//     * 代理Observer{@link Observer}接口，用于捕获onChange(){@link Observer#onChanged(Object)}方法中发生的未知异常。
//     * 当有异常发生时，状态器会将状态置为错误状态{@link State#STATE_ERROR}并标记异常类型为其他{@link ExtData#ERROR_TYPE_OTHER}。
//     * @param <T>
//     */
//    static class ProxyObserver<T> implements Observer<T>{
//
//        Observer<T> mTarget;
//        OnDataChangeObserver mOnDataChangeObserver;
//        LiveDataAttach mLiveDataAttach;
//
//        ProxyObserver(LiveDataAttach liveDataAttach, Observer<T> observer, OnDataChangeObserver onDataChangeObserver){
//            this.mLiveDataAttach = liveDataAttach;
//            this.mTarget = observer;
//            this.mOnDataChangeObserver = onDataChangeObserver;
//        }
//
//        @Override
//        public void onChanged(T t) {
//            try {
//                if(mTarget!=null){
//                    if(mTarget instanceof TagObserver){
//                        ((TagObserver<T>) mTarget).onChanged(t, mLiveDataAttach !=null? mLiveDataAttach.getTag():null);
//                    }else{
//                        mTarget.onChanged(t);
//                    }
//                    if(mOnDataChangeObserver!=null){
//                        mOnDataChangeObserver.onDataChange();
//                    }
//                }
//            }catch (Throwable e){
//                XLog.e("Observer的onChange()方法中发生了异常");
//                e.printStackTrace();
//                if(mLiveDataAttach!=null && mLiveDataAttach.getStateDispatcher()!=null){
//                    mLiveDataAttach.getStateDispatcher().postStateValue(new State(
//                            State.STATE_ERROR, ExtData.ERROR_TYPE_OTHER, null));
//                }
//                ExceptionHelper.exceptionCatch(ExceptionHelper.EXCEPTION_TYPE_LIVE_DATA_OBSERVER,
//                        "", "Observer的onChange()发生异常", e);
//                DebugToast.showShort("Observer的onChange()发生异常");
//                BlackBox.postException(e);
//            }
//        }
//
//    }
//
//    interface OnDataChangeObserver{
//        void onDataChange();
//    }
//}
