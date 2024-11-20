package com.art.cheric.global.error.exception;

import com.art.cheric.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebClientException extends RuntimeException {
	private ErrorCode errorCode;
	private String message;

	public WebClientException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}
