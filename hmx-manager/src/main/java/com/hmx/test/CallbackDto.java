package com.hmx.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallbackDto<T>{

    private String transactionId;
    /**
     * 参数列表
     */
    private Map<String, String> param = new HashMap<String, String>();

    /**
     * 充值返回的参数列表
     */
    private Map<String, List<T>> paramList = new HashMap<String, List<T>>();

    /**
     * 回调地址
     */
    private String url;
    /**
     * 间隔
     */
    private long interval;
    /**
     * 回调次数
     */
    private int num;

    /**
     * 补单次数
     */
    private int tryCount;

    /**
     * 回调类型 1:用户充值代理返现
     */
    private int callbackType;

    public CallbackDto(String transactionId, Map<String, String> param,
                       String url, Integer tryCount) {
        this.transactionId = transactionId;
        this.param = param;
        this.url = url;
        this.interval = 0;
        this.num = 0;
        this.tryCount = tryCount;
    }

    // 用户充值代理返现
    public CallbackDto(String transactionId, Map<String, String> param,
                       Map<String, List<T>> paramList, String url, Integer tryCount,
                       int callbackType) {
        this.transactionId = transactionId;
        this.param = param;
        this.paramList = paramList;
        this.url = url;
        this.interval = 0;

        this.tryCount = tryCount;
        this.setCallbackType(1);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    public int getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(int callbackType) {
        this.callbackType = callbackType;
    }

    public Map<String, List<T>> getParamList() {
        return paramList;
    }

    public void setParamList(Map<String, List<T>> paramList) {
        this.paramList = paramList;
    }

}