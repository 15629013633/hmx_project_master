package com.hmx.utils.result;

/**
 * 具体的 错误信息
 */
public class ResultError {
	
	/**
	 * 字段名称
	 */
	private String name;
	
	/**
	 * 错误信息
	 */
	private String message;

	public ResultError() {
		super();
	}

	public ResultError(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
