package com.hmx.live.entity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/21.
 */
public class LiveModel {
    private Integer PageNum;
    private Integer PageSize;
    private String RequestId;
    private Integer TotalNum;
    private Integer TotalPage;
    private Map<String,ArrayList<LiveStreamOnlineInfo>> OnlineInfo;

    public Integer getPageNum() {
        return PageNum;
    }

    public void setPageNum(Integer pageNum) {
        PageNum = pageNum;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public Integer getTotalNum() {
        return TotalNum;
    }

    public void setTotalNum(Integer totalNum) {
        TotalNum = totalNum;
    }

    public Integer getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(Integer totalPage) {
        TotalPage = totalPage;
    }

    public Map<String, ArrayList<LiveStreamOnlineInfo>> getOnlineInfo() {
        return OnlineInfo;
    }

    public void setOnlineInfo(Map<String, ArrayList<LiveStreamOnlineInfo>> onlineInfo) {
        OnlineInfo = onlineInfo;
    }
}
