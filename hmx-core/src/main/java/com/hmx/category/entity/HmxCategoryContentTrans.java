package com.hmx.category.entity;

import java.util.Date;

/**
 * Created by Administrator on 2019/4/28.
 */
public class HmxCategoryContentTrans {
    private Integer categoryContentId;
    private Integer categoryId;
    private String categoryTitle;
    private String categoryContent;
    private Integer contentType;
    private String contentImages;
    private String movieId;
    private Integer musicId;
    private Integer browseNum;
    private Date createTime;
    private Date newTime;
    private Integer state;
    private Integer version;
    private Integer createid;
    private String fileUrl;

    public HmxCategoryContentTrans() {
        super();
    }

    public Integer getCategoryContentId() {
        return categoryContentId;
    }

    public void setCategoryContentId(Integer categoryContentId) {
        this.categoryContentId = categoryContentId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryContent() {
        return categoryContent;
    }

    public void setCategoryContent(String categoryContent) {
        this.categoryContent = categoryContent;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContentImages() {
        return contentImages;
    }

    public void setContentImages(String contentImages) {
        this.contentImages = contentImages;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
