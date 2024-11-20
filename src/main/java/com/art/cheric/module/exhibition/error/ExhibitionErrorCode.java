package com.art.cheric.module.exhibition.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExhibitionErrorCode implements ErrorCode {
    DUPLICATE_ART_ID(HttpStatus.BAD_REQUEST, "작품 ID는 한 요청에 한 개만 수용이 가능합니다."),
    INVALID_CLOUD_VISION_TYPE(HttpStatus.BAD_REQUEST, "지원하는 Cloud Vision Type이 아닙니다."),
    INVALID_URL_PATH(HttpStatus.BAD_REQUEST, "요청한 작품의 이미지 경로가 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExhibitionErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
