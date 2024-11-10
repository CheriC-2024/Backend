package com.art.cheric.module.file.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FileErrorCode implements ErrorCode {
    INVALID_FILE_NAME(HttpStatus.BAD_REQUEST, "유효한 파일 이름이 아닙니다. 파일 이름에 문자, 숫자, _, - 이외의 값을 넣지 마세요"),
    NEED_EXTENSION(HttpStatus.BAD_REQUEST, "파일 확장자를 같이 보내주세요"),
    INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "유효한 파일 확장자가 아닙니다."),
    FILE_NAME_LENGTH_ERROR(HttpStatus.BAD_REQUEST, "파일 이름 길이가 허용 범위보다 깁니다."),
    FILE_SIZE_ERROR(HttpStatus.BAD_REQUEST, "파일 사이즈가 허용 크기보다 큽니다.");

    private final HttpStatus httpStatus;
    private final String message;

    FileErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
