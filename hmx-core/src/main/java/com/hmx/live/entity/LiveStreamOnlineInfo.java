package com.hmx.live.entity;

/**
 * Created by Administrator on 2019/7/21.
 */
public class LiveStreamOnlineInfo {
    private String AppName;
    private String DomainName;
    private String PublishTime;
    private String PublishUrl;
    private String StreamName;

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getDomainName() {
        return DomainName;
    }

    public void setDomainName(String domainName) {
        DomainName = domainName;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getPublishUrl() {
        return PublishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        PublishUrl = publishUrl;
    }

    public String getStreamName() {
        return StreamName;
    }

    public void setStreamName(String streamName) {
        StreamName = streamName;
    }
}
