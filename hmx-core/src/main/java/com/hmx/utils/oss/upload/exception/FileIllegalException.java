package com.hmx.utils.oss.upload.exception;

/**
 * 文件 类型非法
 */
@SuppressWarnings("serial")
public class FileIllegalException extends UploadException {
	public FileIllegalException(String message) {
        super(message);
    }

    public FileIllegalException(String message, Throwable cause) {
        super(message, cause);
    }
}
