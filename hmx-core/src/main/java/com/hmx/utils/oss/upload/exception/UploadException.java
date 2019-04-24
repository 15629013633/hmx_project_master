package com.hmx.utils.oss.upload.exception;

/**
 * @author 文件上传 组件 异常的父类
 */
@SuppressWarnings("serial")
public class UploadException extends RuntimeException {
	
	public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
