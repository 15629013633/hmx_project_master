package com.hmx.utils.result;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.google.common.collect.Maps;

/**
 * @author bing
 * 结果实体
 */
@JsonSerialize(include=Inclusion.NON_NULL)
public class ResultBean {
	
	/**
	 * 结果码
	 */
	private Integer code;
	
	/**
	 * 消息
	 */
	private String content;
	
	
	private Object entity;
	
	private Object pageBean;
	
	
	private Map<String, Object> result ;
	

	/**
	 * 错误的具体信息
	 */
	private List<ResultError> errData;
	
	private boolean timeOut = false;

	public ResultBean() {
		
	}
	
	public ResultBean( Integer code,boolean timeOut) {
		super();
		this.code = code;
		this.timeOut = timeOut;
	}

	public ResultBean(String content) {
		super();
		this.code = Config.SUCCESS_CODE;
		this.content = content;
	}
	
	public ResultBean(Integer code , String content , Map<String, Object> maps) {
		super();
		this.code = code;
		this.content = content;
		this.result = maps;
	}
	

	public ResultBean(Integer code, String content) {
		super();
		this.code = code;
		this.content = content;
	}
	
	public ResultBean(Integer code, String content, String no) {
		super();
		this.code = code;
		this.content = content;
		put("number", no);
	}

	public ResultBean(Integer code, List<ResultError> data) {
		super();
		this.code = code;
		this.errData = data;
	}
	public ResultBean(Integer code, List<ResultError> data,String no) {
		super();
		this.code = code;
		this.errData = data;
		put("number", no);
	}

	public ResultBean(Integer code, String content, List<ResultError> data) {
		super();
		this.code = code;
		this.content = content;
		this.errData = data;
	}


	public String getContent() {
		return content;
	}

	public ResultBean setContent(String content) {
		this.content = content;
		return this;
	}

	public List<ResultError> getErrData() {
		return errData;
	}

	public ResultBean setErrData(List<ResultError> data) {
		this.errData = data;
		return this;
	}

	public boolean isTimeOut() {
		return timeOut;
	}

	public ResultBean setTimeOut(boolean timeOut) {
		this.timeOut = timeOut;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public ResultBean setCode(Integer code) {
		this.code = code;
		return this;
	}

	public Object getPageBean() {
		return pageBean;
	}

	public ResultBean setPageBean(Object pageBean) {
		this.pageBean = pageBean;
		return this;
	}
	
	public ResultBean put(String key , Object value) {
		if ( this.result == null ) {
			result = Maps.newHashMap();
		}
		result.put(key, value);
		return this;
	}
	
	public Map<String, Object> getResult() {
		return result;
	}

	public ResultBean setResult(Map<String, Object> result) {
		this.result = result;
		return this;
	}
	
	public Object getEntity() {
		return entity;
	}

	public ResultBean setEntity(Object entity) {
		this.entity = entity;
		return this;
	}
}
