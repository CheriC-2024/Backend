package com.art.cheric.module.exhibition.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExhibitionErrorCode implements ErrorCode {
    DUPLICATE_ART_ID(HttpStatus.BAD_REQUEST, "작품 ID는 한 요청에 한 개만 수용이 가능합니다."),
    INVALID_CLOUD_VISION_TYPE(HttpStatus.BAD_REQUEST, "지원하는 Cloud Vision Type이 아닙니다."),
    INVALID_URL_PATH(HttpStatus.BAD_REQUEST, "요청한 작품의 이미지 경로가 유효하지 않습니다."),
    EXHIBITION_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 전시가 존재하지 않습니다."),
    EXHIBITION_HEART_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 해당 전시에 하트 표시를 하셨습니다."),
    EXHIBITION_HEART_DOESNT_EXIST(HttpStatus.NOT_FOUND, "해당 전시에 하트 표시를 한 적이 없어 취소할 수 없습니다."),
    REVIEW_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 전시 댓글이 존재하지 않습니다."),
    EXHIBITION_REVIEW_HEART_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 해당 댓글에 하트 표시를 하셨습니다."),
    EXHIBITION_REVIEW_HEART_DOESNT_EXIST(HttpStatus.NOT_FOUND, "해당 댓글에 하트 표시를 한 적이 없어 취소할 수 없습니다."),
    EXHIBITION_BACKGROUND_INVALID(HttpStatus.BAD_REQUEST, "전시의 배경은 이미지 혹은 색상으로 설정되어야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExhibitionErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
