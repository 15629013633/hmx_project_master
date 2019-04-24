package com.hmx.verifylog.entity;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

public class HmxVerifylog{
    private Integer verifyLogId;
    private Integer verifyType;
    private String verifyCode;
    private String verifyObject;
    private Date addTime;
    private Integer isVerify;

    public HmxVerifylog() {
		super();
	}

    public HmxVerifylog(Integer verifyLogId, Integer verifyType, String verifyCode, String verifyObject, Date addTime, Integer isVerify) {
		super();
		this.verifyLogId = verifyLogId;
		this.verifyType = verifyType;
		this.verifyCode = verifyCode;
		this.verifyObject = verifyObject;
		this.addTime = addTime;
		this.isVerify = isVerify;
	}
	
    public Integer getVerifyLogId() {
		return verifyLogId;
	}
	
	public void setVerifyLogId(Integer verifyLogId){
		this.verifyLogId = verifyLogId;
	}
	
    public Integer getVerifyType() {
		return verifyType;
	}
	
	public void setVerifyType(Integer verifyType){
		this.verifyType = verifyType;
	}
	
    public String getVerifyCode() {
		return verifyCode;
	}
	
	public void setVerifyCode(String verifyCode){
		this.verifyCode = verifyCode;
	}
	
    public String getVerifyObject() {
		return verifyObject;
	}
	
	public void setVerifyObject(String verifyObject){
		this.verifyObject = verifyObject;
	}
	
    public Date getAddTime() {
		return addTime;
	}
	
	public void setAddTime(Date addTime){
		this.addTime = addTime;
	}
	
    public Integer getIsVerify() {
		return isVerify;
	}
	
	public void setIsVerify(Integer isVerify){
		this.isVerify = isVerify;
	}
	
}