package com.hmx.system.entity;

import java.util.Date;

/**
 * Created by Administrator on 2019/5/2.
 */
public class Comment {
    private Integer commentId;   //评论id
    private String content;    //评论内容
    private Integer categoryContentId;    //评论的内容的id
    private String userId;    //评论人id
    private String parentId;   //父评论id
    private Date createDate;    //评论时间
    private Integer status;   //0评论他人不可见，默认     1评论他人可见    管理员审核为不可见只要将该值置为0就好

    public Comment(){
        super();
    }


    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategoryContentId() {
        return categoryContentId;
    }

    public void setCategoryContentId(Integer categoryContentId) {
        this.categoryContentId = categoryContentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
