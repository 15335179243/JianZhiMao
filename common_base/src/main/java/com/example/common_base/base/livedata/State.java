package com.example.common_base.base.livedata;

import com.example.common_base.base.ExtData;

/**
 * ChuMuProjectFramework
 * <p>
 * Created by ChuMu on 2020/5/26
 * Copyright © 2020年 ChuMu. All rights reserved.
 * <p>
 *
 * 状态流转类型：
 * 1.STATE_START-->STATE_RESULT                         （正常-成功）
 * 2.STATE_START-->STATE_RESULT-->STATE_RESULT_NULL_DATA（正常-空数据）
 * 3.STATE_START-->STATE_RESULT-->STATE_ERROR           （异常-数据成功返回，onChange中发生异常，状态被标为ERROR）
 * 4.STATE_START-->STATE_ERROR                          （异常-数据未成功返回）
 *
 */
public final class State {

    public static final int STATE_START = 1;
    public static final int STATE_RESULT = 2;
    public static final int STATE_RESULT_NULL_DATA = 3;
    public static final int STATE_ERROR = -1;

    public int state;

    public int extType;

    public ExtData extData;

    public State(int state) {
        this(state, -1, new ExtData());
    }

    public State(int state, int extType, ExtData extData) {
        this.state = state;
        this.extType = extType;
        this.extData = extData;
    }

    public final String businessMessage(){
        if(extData !=null)
            return extData.businessMessage;
        return "";
    }

    public final String httpMessage(){
        if(extData !=null)
            return extData.httpMessage;
        return "";
    }

    public final String stateString(int state){
        switch (state){
            case STATE_START:
                return "START";
            case STATE_RESULT:
                return "RESULT";
            case STATE_ERROR:
                return "ERROR";
            case STATE_RESULT_NULL_DATA:
                return "RESULT_NULL_DATA";
        }
        return "ILLEGAL_STATE";
    }
}
