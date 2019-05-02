package com.hmx.system.entity;

import java.util.Date;

/**
 * 消息实体类
 * Created by Administrator on 2019/5/2.
 */
public class Message {
    private Integer messageId;
    private String title;    //消息标题
    private String content;   //消息内容
    private String fromUserId;  //消息发起人id
    private String targeUserId;   //目标人Id  发送给targeUserId
    private Date createDate;    //发消息时间
    private Integer status;   //消息状态   0未读 1已读

    public Message(){
        super();
    }

    public Message(Integer messageId,String title,String content,String fromUserId,
                   String targeUserId,Date createDate,Integer status){
        super();
        this.messageId = messageId;
        this.title = title;
        this.fromUserId = fromUserId;
        this.targeUserId = targeUserId;
        this.content = content;
        this.createDate =createDate;
        this.status = status;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getTargeUserId() {
        return targeUserId;
    }

    public void setTargeUserId(String targeUserId) {
        this.targeUserId = targeUserId;
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
