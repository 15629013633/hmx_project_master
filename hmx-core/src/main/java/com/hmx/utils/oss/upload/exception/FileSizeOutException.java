package com.hmx.utils.oss.upload.exception;

/**
 * 文件 过大
 */
@SuppressWarnings("serial")
public class FileSizeOutException extends UploadException {
	public FileSizeOutException(String message) {
        super(message);
    }

    public FileSizeOutException(String message, Throwable cause) {
        super(message, cause);
    }
}
