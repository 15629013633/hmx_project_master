package com.hmx.category.entity;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class HmxCategoryContent{
    private Integer categoryContentId;
    private Integer categoryId;
    private String categoryTitle;
    private String subTitle;   //副标题
    private String contentDesc;   //内容简介
    private String categoryContent;
    private Integer contentType;
    private String contentImages;
    private Integer movieId;
    private Integer musicId;
    private Integer browseNum;
    private Date createTime;
    private Date newTime;
    private Integer state;
    private Integer version;
    private Integer createid;
	private Integer mode;      //内容展现方式    1普通类型   2轮播图类型  3首页大图类型

    public HmxCategoryContent() {
		super();
	}

    
    public HmxCategoryContent(Integer categoryContentId, Integer categoryId, String categoryTitle,Integer mode,
			String categoryContent, Integer contentType, String contentImages, Integer movieId, Integer musicId,
			Integer browseNum, Date createTime, Date newTime, Integer state, Integer version, Integer createid,String subTitle,String contentDesc) {
		super();
		this.categoryContentId = categoryContentId;
		this.categoryId = categoryId;
		this.categoryTitle = categoryTitle;
		this.categoryContent = categoryContent;
		this.contentType = contentType;
		this.contentImages = contentImages;
		this.movieId = movieId;
		this.musicId = musicId;
		this.browseNum = browseNum;
		this.createTime = createTime;
		this.newTime = newTime;
		this.state = state;
		this.version = version;
		this.createid = createid;
		this.subTitle = subTitle;
		this.contentDesc = contentDesc;
		this.mode = mode;
	}


	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	public Integer getCategoryContentId() {
		return categoryContentId;
	}
	
	public void setCategoryContentId(Integer categoryContentId){
		this.categoryContentId = categoryContentId;
	}
	
    public String getCategoryTitle() {
		return categoryTitle;
	}
	
	public void setCategoryTitle(String categoryTitle){
		this.categoryTitle = categoryTitle;
	}
	
    public String getCategoryContent() {
		return categoryContent;
	}
	
	public void setCategoryContent(String categoryContent){
		this.categoryContent = categoryContent;
	}
	
    public Integer getContentType() {
		return contentType;
	}
	
	public void setContentType(Integer contentType){
		this.contentType = contentType;
	}
	
    public String getContentImages() {
		return contentImages;
	}
	
	public void setContentImages(String contentImages){
		this.contentImages = contentImages;
	}
	
    public Integer getMovieId() {
		return movieId;
	}
	
	public void setMovieId(Integer movieId){
		this.movieId = movieId;
	}
	
    public Integer getMusicId() {
		return musicId;
	}
	
	public void setMusicId(Integer musicId){
		this.musicId = musicId;
	}
	
    public Integer getBrowseNum() {
		return browseNum;
	}
	
	public void setBrowseNum(Integer browseNum){
		this.browseNum = browseNum;
	}
	
    public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
    public Date getNewTime() {
		return newTime;
	}
	
	public void setNewTime(Date newTime){
		this.newTime = newTime;
	}
	
    public Integer getState() {
		return state;
	}
	
	public void setState(Integer state){
		this.state = state;
	}
	
    public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version){
		this.version = version;
	}
	
    public Integer getCreateid() {
		return createid;
	}
	
	public void setCreateid(Integer createid){
		this.createid = createid;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}
}