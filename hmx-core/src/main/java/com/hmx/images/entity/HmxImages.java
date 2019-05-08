package com.hmx.images.entity;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class HmxImages{
    private Integer imageId;
    private String imageUrl;   //大海报    主要用于pc端图片显示大横图
    private Date createTime;
    private Date newTime;
    private Integer state;
    private Integer version;
    private Integer createid;
	private Integer categoryContentId;
	private String transImage;   //横图
	private String verticalImage;   //竖图

    public HmxImages() {
		super();
	}

    public HmxImages(Integer imageId, String imageUrl, Date createTime, Date newTime, Integer state, Integer version, Integer createid,
					 Integer categoryContentId,String transImage,String verticalImage) {
		super();
		this.imageId = imageId;
		this.imageUrl = imageUrl;
		this.createTime = createTime;
		this.newTime = newTime;
		this.state = state;
		this.version = version;
		this.createid = createid;
		this.categoryContentId = categoryContentId;
		this.verticalImage = verticalImage;
		this.transImage = transImage;
	}
	
    public Integer getImageId() {
		return imageId;
	}
	
	public void setImageId(Integer imageId){
		this.imageId = imageId;
	}
	
    public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
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

	public Integer getCategoryContentId() {
		return categoryContentId;
	}

	public void setCategoryContentId(Integer categoryContentId) {
		this.categoryContentId = categoryContentId;
	}

	public String getTransImage() {
		return transImage;
	}

	public void setTransImage(String transImage) {
		this.transImage = transImage;
	}

	public String getVerticalImage() {
		return verticalImage;
	}

	public void setVerticalImage(String verticalImage) {
		this.verticalImage = verticalImage;
	}
}