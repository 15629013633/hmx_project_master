package com.hmx.system.entity;

/**
 * Created by songjinbao on 2019/5/30.
 */
public class Tagtab {
    private Integer tagId;//标签id
    private String tagName;//标签名称
    private long createTime;//创建时间

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
