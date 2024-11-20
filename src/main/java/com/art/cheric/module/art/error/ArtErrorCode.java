package com.art.cheric.module.art.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ArtErrorCode implements ErrorCode {
    INVALID_CHERRY_PRICE(HttpStatus.BAD_REQUEST, "작가 작품에는 체리가 필수로 들어가져야 합니다."),
    ART_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작품을 찾을 수 없습니다."),
    OWN_ART_NOT_FOUND(HttpStatus.NOT_FOUND, "소장 작품을 찾을 수 없습니다."),
    ARTIST_ART_NOT_FOUND(HttpStatus.NOT_FOUND, "작가 작품을 찾을 수 없습니다."),;

    private final HttpStatus httpStatus;
    private final String message;

    ArtErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
