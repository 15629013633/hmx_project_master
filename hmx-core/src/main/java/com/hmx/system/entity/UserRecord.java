package com.hmx.system.entity;

/**
 * 用户阅读消息记录
 * Created by Administrator on 2019/6/12.
 */
public class UserRecord {
    private Integer id;
    private Integer contentId;   //内容id
    private String userId;   //用户id
    private long createTime;   //阅读时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
