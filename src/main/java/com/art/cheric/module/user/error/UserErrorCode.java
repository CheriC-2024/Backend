package com.art.cheric.module.user.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    ID_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "Id Token이 필요합니다."),
    DEVICE_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "Device Id가 필요합니다."),
    FCM_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "Fcm Token이 필요합니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다."),
    INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 Id Token이 아닙니다."),
    NAME_DUPLICATED(HttpStatus.CONFLICT, "기본 사용자의 닉네임은 중복될 수 없습니다."),
    NAME_REQUIRED(HttpStatus.BAD_REQUEST, "사용자 이름은 필수 값입니다."),
    NAME_SIZE_ERROR(HttpStatus.BAD_REQUEST, "사용자 이름은 2자 이상 10자 미만이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
