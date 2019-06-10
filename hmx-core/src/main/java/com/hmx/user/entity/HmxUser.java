package com.hmx.user.entity;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class HmxUser{
    private Integer id;
    private String userName;
    private String userAliase;
    private String userPhone;
    private String password;
    private Integer gender;
    private String headPic;
    private Date createTime;
    private Date newTime;
    private Integer state;
    private Integer version;
    private Integer createid;
	private Integer type;
	private String wxId;
	private String qqId;

    public HmxUser() {
		super();
	}

    public HmxUser(Integer id, String userName, String userAliase, String userPhone, String password, Integer gender, String headPic, Date createTime, Date newTime, Integer state, Integer version, Integer createid, Integer type) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAliase = userAliase;
		this.userPhone = userPhone;
		this.password = password;
		this.gender = gender;
		this.headPic = headPic;
		this.createTime = createTime;
		this.newTime = newTime;
		this.state = state;
		this.version = version;
		this.createid = createid;
		this.type = type;
	}
	
    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
    public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
    public String getUserAliase() {
		return userAliase;
	}
	
	public void setUserAliase(String userAliase){
		this.userAliase = userAliase;
	}
	
    public String getUserPhone() {
		return userPhone;
	}
	
	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}
	
    public String getPassword() {
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
    public Integer getGender() {
		return gender;
	}
	
	public void setGender(Integer gender){
		this.gender = gender;
	}
	
    public String getHeadPic() {
		return headPic;
	}
	
	public void setHeadPic(String headPic){
		this.headPic = headPic;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
}