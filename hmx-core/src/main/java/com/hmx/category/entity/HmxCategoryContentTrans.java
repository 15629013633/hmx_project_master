package com.hmx.category.entity;

import com.hmx.files.entity.HmxFiles;
import com.hmx.images.entity.HmxImages;
import com.hmx.movie.entity.HmxMovie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/4/28.
 */
public class HmxCategoryContentTrans {
    private Integer categoryContentId;
    private Integer categoryId;
    private Integer categoryParentId;
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
    private String contentDesc;
    private String subTitle;
    private Integer mode;
    private String contentFlow;
    private Integer sort;    //排序
    private String tagId;  //标签id,以逗号分隔
    private Integer sourceId;  //来源id
    private String tagName;   //标签名称
    private String sourceTitle;     //来源名称
    private Integer isPublish;    //是否发布   0未发布  1发布   默认0
    private Integer isShowHomePage;     //是否在首页展示   0展示   1不展示  默认0
    private Integer userLevel;    //内容级别，与用户级别对应   默认0
//    private String transImage;   //横图
//    private String verticalImage;   //竖图
    private String categoryName;
    private List<HmxMovie> movieList = new ArrayList<>();
    private List<HmxImages> imagesList = new ArrayList<>();
    private List<HmxFiles> filesList = new ArrayList<>();

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

    public List<HmxMovie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<HmxMovie> movieList) {
        this.movieList = movieList;
    }

    public List<HmxImages> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<HmxImages> imagesList) {
        this.imagesList = imagesList;
    }

    public List<HmxFiles> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<HmxFiles> filesList) {
        this.filesList = filesList;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getContentFlow() {
        return contentFlow;
    }

    public void setContentFlow(String contentFlow) {
        this.contentFlow = contentFlow;
    }

//    public String getTransImage() {
//        return transImage;
//    }
//
//    public void setTransImage(String transImage) {
//        this.transImage = transImage;
//    }
//
//    public String getVerticalImage() {
//        return verticalImage;
//    }
//
//    public void setVerticalImage(String verticalImage) {
//        this.verticalImage = verticalImage;
//    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public Integer getIsShowHomePage() {
        return isShowHomePage;
    }

    public void setIsShowHomePage(Integer isShowHomePage) {
        this.isShowHomePage = isShowHomePage;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(Integer categoryParentId) {
        this.categoryParentId = categoryParentId;
    }
}
