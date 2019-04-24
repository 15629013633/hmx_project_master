package com.hmx.utils.oss.upload.exception;

/**
 * 文件为空
 */
@SuppressWarnings("serial")
public class FileIsNullException extends UploadException {
	public FileIsNullException(String message) {
        super(message);
    }

    public FileIsNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
