package com.hmx.utils.oss.upload.exception;

/**
 * 文件 过小
 */
@SuppressWarnings("serial")
public class FileSizeSmallException extends UploadException {
	public FileSizeSmallException(String message) {
        super(message);
    }

    public FileSizeSmallException(String message, Throwable cause) {
        super(message, cause);
    }
}
