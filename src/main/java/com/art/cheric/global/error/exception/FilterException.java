package com.art.cheric.global.error.exception;

import com.art.cheric.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public FilterException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
