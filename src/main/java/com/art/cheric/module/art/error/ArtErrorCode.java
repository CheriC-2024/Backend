package com.art.cheric.module.art.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ArtErrorCode implements ErrorCode {
    ART_HEART_DOESNT_EXIST(HttpStatus.NOT_FOUND, "해당 작품에 하트 표시를 한 적이 없어 취소할 수 없습니다."),
    ART_HEART_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 해당 작품에 하트 표시를 하셨습니다."),
    INVALID_CHERRY_PRICE(HttpStatus.BAD_REQUEST, "작가 작품에는 체리가 필수로 들어가져야 합니다."),
    ART_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작품을 찾을 수 없습니다."),
    ARTS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 작품 리스트 중 찾을 수 없는 작풉이 있습니다."),
    YOUR_OWN_ART_NOT_FOUND(HttpStatus.NOT_FOUND, "본인 소장 작품을 찾을 수 없습니다."),
    OWN_ART_NOT_FOUND(HttpStatus.NOT_FOUND, "소장 작품을 찾을 수 없습니다."),
    ARTIST_ART_NOT_FOUND(HttpStatus.NOT_FOUND, "작가 작품을 찾을 수 없습니다."),
    ARTIST_ART_INVALID(HttpStatus.FORBIDDEN, "해당 작가의 작품은 이제 사용할 수 없습니다."),
    OWN_ART_INVALID(HttpStatus.FORBIDDEN, "해당 소장 작품은 현재 인증이 되지 않았습니다."),
    IS_NOT_OWN_ART(HttpStatus.BAD_REQUEST, "소장 작품이 아닙니다."),;

    private final HttpStatus httpStatus;
    private final String message;

    ArtErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
