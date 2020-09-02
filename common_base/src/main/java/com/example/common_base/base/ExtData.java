package com.example.common_base.base;

/**
 * ChuMuProjectFramework
 * <p>
 * Created by ChuMu on 2020/5/26
 * Copyright © 2020年 ChuMu. All rights reserved.
 * <p>
 * Description:     请求状态、消息数据类
 */
public final class ExtData {

    /**
     * 默认无状态
     */
    public static final int TYPE_DEFAULT = 0;
    /**
     * 成功状态
     */
    public static final int TYPE_SUCCESS = 1;

    /**
     * HTTP成功，业务响应错误
     */
    public static final int ERROR_TYPE_RESPONSE_BUSINESS = 10;
    /**
     * HTTP失败，比如502,404
     */
    public static final int ERROR_TYPE_RESPONSE_HTTP = 11;
    /**
     * 网络错误
     */
    public static final int ERROR_TYPE_NETWORK = 12;
    /**
     * HTTP成功，数据解析错误
     */
    public static final int ERROR_TYPE_PARSE = 13;
    /**
     * 请求超时
     */
    public static final int ERROR_TYPE_TIMEOUT = 14;
    /**
     * SSLHandShakeException
     */
    public static final int ERROR_TYPE_SSL_HAND_SHAKE = 15;
    /**
     * 请求过程抓取到的其他异常
     */
    public static final int ERROR_TYPE_CATCH_EXCEPTION = 16;
    /**
     * 其他类型错误
     */
    public static final int ERROR_TYPE_OTHER = 20;


    /**
     * 错误类型为其他时 ERROR_TYPE_OTHER 的子类型
     */
    public int otherSubType;

    /**
     * 状态类型
     */
    public int extType;

    /**
     * HTTP响应码
     */
    public int httpCode;
    /**
     * HTTP响应消息
     */
    public String httpMessage;
    /**
     * 业务响应码
     */
    public int businessCode;
    /**
     * 业务消息
     */
    public String businessMessage;
    /**
     * 响应体数据
     */
    public byte[] resultBytes;
    /**
     * 额外的消息
     */
    public String extraMessage;
    /**
     * 对应的请求信息
     */
    private HttpRequest request;

    void attachRequest(HttpRequest request){
       this.request = request;
    }

    public ExtData() {
        this(TYPE_DEFAULT, -1,null,-1,null, null, null);
    }

    /**
     * 创建一个<<其他错误信息>>的数据类
     * @param extraMessage
     * @return
     */
    public static ExtData buildOtherErrorExtra(String extraMessage){
        return buildOtherErrorExtra(extraMessage, 0);
    }

    /**
     * 创建一个<<其他错误信息>>的数据类
     * @param extraMessage
     * @param otherSubType
     * @return
     */
    public static ExtData buildOtherErrorExtra(String extraMessage, int otherSubType){
        ExtData extData = new ExtData(ERROR_TYPE_OTHER, -1, null, -1, null, null, extraMessage);
        extData.otherSubType = otherSubType;
        return extData;
    }

    /**
     * 创建一个<<请求过程抓取到的其他异常>>的数据类
     * @param extraMessage
     * @return
     */
    public static ExtData buildCatchExceptionExtra(String extraMessage){
        return new ExtData(ERROR_TYPE_CATCH_EXCEPTION, -1, null, -1, null, null, extraMessage);
    }

    /**
     * 创建一个超时错误数据类
     * @return
     */
    public static ExtData buildTimeoutErrorExtra(){
        return new ExtData(ERROR_TYPE_TIMEOUT, -1, "Timeout", -1, null, null, null);
    }

    /**
     * 创建一个SSLHandShakeException错误数据类
     * @return
     */
    public static ExtData buildSSLHandShakeErrorExtra(){
        return new ExtData(ERROR_TYPE_SSL_HAND_SHAKE, -1, "SSLHandShake", -1, null, null, null);
    }

    /**
     * 创建一个网络错误类型的数据类
     * @return
     */
    static ExtData buildNetErrorExtra(){
        return new ExtData(ERROR_TYPE_NETWORK, -1, null, -1, null, null, null);
    }

    /**
     * 创建一个业务错误类型的数据类
     * @return
     */
    public static ExtData buildBusinessErrorExtra(int businessCode, String businessMessage){
        return new ExtData(ExtData.ERROR_TYPE_RESPONSE_BUSINESS, -1, null, businessCode, businessMessage, null, null);
    }

    /**
     * 创建一个HTTP错误类型的数据类
     * @return
     */
    public static ExtData buildHttpErrorExtra(int httpCode, String httpMessage){
        return new ExtData(ExtData.ERROR_TYPE_RESPONSE_HTTP, httpCode, httpMessage, -1, null, null, null);
    }

    ExtData(int extType, int httpCode, String httpMessage, int businessCode, String businessMessage, byte[] resultBytes, String extraMessage) {
        this.extType = extType;
        this.httpCode = httpCode;
        this.httpMessage = httpMessage;
        this.businessCode = businessCode;
        this.businessMessage = businessMessage;
        this.resultBytes = resultBytes;
        this.extraMessage = extraMessage;
    }

    boolean noError(){
        return extType == TYPE_SUCCESS || extType == TYPE_DEFAULT;
    }

    public HttpRequest getRequest(){
        return request;
    }
}
