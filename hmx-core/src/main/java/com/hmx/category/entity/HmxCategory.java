package com.hmx.category.entity;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class HmxCategory{
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;
    private Integer sort;
    private Integer isClose;
    private Date createTime;
    private Date newTime;
    private Integer state;
    private Integer version;
    private Integer createid;
    private Integer parentId;      //一级分类的id

    public HmxCategory() {
		super();
	}

	
    public HmxCategory(Integer categoryId, String categoryName, Integer categoryType, Integer sort, Integer isClose,
			Date createTime, Date newTime, Integer state, Integer version, Integer createid,Integer parentId) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryType = categoryType;
		this.sort = sort;
		this.isClose = isClose;
		this.createTime = createTime;
		this.newTime = newTime;
		this.state = state;
		this.version = version;
		this.createid = createid;
		this.categoryType = categoryType;
		this.parentId = parentId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Integer categoryId){
		this.categoryId = categoryId;
	}
	
    public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}
	
    public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort){
		this.sort = sort;
	}
	
    public Integer getIsClose() {
		return isClose;
	}
	
	public void setIsClose(Integer isClose){
		this.isClose = isClose;
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

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}