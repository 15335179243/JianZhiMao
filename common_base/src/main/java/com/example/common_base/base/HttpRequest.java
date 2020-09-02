package com.example.common_base.base;

import java.util.HashMap;
import java.util.Map;

/**
 * ChuMuProjectFramework
 * <p>
 * Created by ChuMu on 2020/5/26
 * Copyright © 2020年 ChuMu. All rights reserved.
 * <p>
 * Description:
 */
public class HttpRequest {

    static final String METHOD_GET = "GET";
    static final String METHOD_POST = "POST";

    static final String SCHEME_HTTP = "HTTP";
    static final String SCHEME_HTTPS = "HTTPS";

    private String method;
    private String url;
    private Map<String,Object> mHeaders = new HashMap<>();
    private Map<String,Object> mParams = new HashMap<>();
    private String mJsonParams = "";

    public HttpRequest addHeader(String key, Object value){
        mHeaders.put(key, value);
        return this;
    }

    public HttpRequest addParams(String key, Object value){
        mParams.put(key, value);
        return this;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.mHeaders = headers;
    }

    public void setParams(Map<String, Object> params) {
        this.mParams = params;
    }

    public Map<String, Object> getHeaders() {
        return mHeaders;
    }

    public Map<String, Object> getParams() {
        return mParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getJsonParams() {
        return mJsonParams;
    }

    public void setJsonParams(String jsonParams) {
        this.mJsonParams = jsonParams;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", mHeaders=" + mHeaders +
                ", mParams=" + mParams +
                ", mJsonParams='" + mJsonParams + '\'' +
                '}';
    }
}
