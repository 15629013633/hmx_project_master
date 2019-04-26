package com.hmx.files.entity;

import java.util.Date;

/**
 * Created by Administrator on 2019/4/26.
 */
public class HmxFiles {
    private Integer fileId;
    private String fileUrl;
    private Date createTime;
    private Integer state;
    private Integer version;
    private Integer createid;
    private Integer categoryContentId;

    public HmxFiles(){
        super();
    }

    public HmxFiles(Integer fileId,String fileUrl,Date createTime,Integer state,Integer version,Integer createid,Integer categoryContentId){
        super();
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.createTime = createTime;
        this.state = state;
        this.version = version;
        this.createid = createid;
        this.categoryContentId = categoryContentId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCreateid() {
        return createid;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    public Integer getCategoryContentId() {
        return categoryContentId;
    }

    public void setCategoryContentId(Integer categoryContentId) {
        this.categoryContentId = categoryContentId;
    }
}
