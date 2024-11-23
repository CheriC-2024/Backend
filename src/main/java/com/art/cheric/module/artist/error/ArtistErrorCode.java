package com.art.cheric.module.artist.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ArtistErrorCode implements ErrorCode {
    ARTIST_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 작가 등록 단계(인증 전, 인증 진행 중, 인증 완료 등)에 있는 사용자입니다."),
    ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자는 작가가 아닙니다."),
    ARTIST_NON_VALID(HttpStatus.FORBIDDEN, "해당 사용자는 아직 작가 인증이 되지 않았습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ArtistErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
