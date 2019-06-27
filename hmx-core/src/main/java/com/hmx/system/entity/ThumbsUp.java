package com.hmx.system.entity;

/**
 * 点赞
 * Created by Administrator on 2019/6/27.
 */
public class ThumbsUp {
    private Integer thumbsId;   //点赞分id
    private String userPhone;   //用户手机号
    private Integer contentId;  //内容id
    private long createTime; //评分时间

    public Integer getThumbsId() {
        return thumbsId;
    }

    public void setThumbsId(Integer thumbsId) {
        this.thumbsId = thumbsId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
