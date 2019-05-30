package com.hmx.system.entity;

/**
 * Created by Administrator on 2019/5/30.
 */
public class SourceModel {
    private Integer sourceId;
    private String title;
    private long createTime;//创建时间

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
