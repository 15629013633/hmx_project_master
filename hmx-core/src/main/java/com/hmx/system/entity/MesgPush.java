package com.hmx.system.entity;

/**
 * 消息推送实体类
 * Created by Administrator on 2019/6/11.
 */
public class MesgPush {
    private Integer contentId;  //内容id
    private String contentImage;  //内容图片
    private Integer contentTpye;  //类型  1文本  2图文  3视频
    private String title;    //标题
    private String subTitle;  //副标题
    private String contentDes;  //内容简介
    private String extend;      //扩展字段
    private Integer status;   //推送了则为1，未推送则为0  默认为0
    private long createTime;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public Integer getContentTpye() {
        return contentTpye;
    }

    public void setContentTpye(Integer contentTpye) {
        this.contentTpye = contentTpye;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentDes() {
        return contentDes;
    }

    public void setContentDes(String contentDes) {
        this.contentDes = contentDes;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
