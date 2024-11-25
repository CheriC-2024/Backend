package com.art.cheric.module.following.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FollowErrorCode implements ErrorCode {
    INVALID_FOLLOWED_ID(HttpStatus.BAD_REQUEST, "자신을 팔로우할 수 없습니다."),
    FOLLOW_DUPLICATED(HttpStatus.CONFLICT, "이미 해당 사용자를 팔로우 중입니다."),
    NON_FOLLOWING(HttpStatus.NOT_FOUND, "팔로잉중인 사용자가 없습니다."),
    NON_FOLLOW(HttpStatus.NOT_FOUND, "팔로우중인 사용자가 없습니다."),
    DOESNT_FOLLOW_YET(HttpStatus.BAD_REQUEST, "아직 팔로우 하지 않아, 팔로우를 취소할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    FollowErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
