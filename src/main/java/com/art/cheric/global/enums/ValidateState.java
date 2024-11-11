package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ValidateState {
    VALID("인증 성공"),
    VALID_YET("인증 전"),
    NOW_VALIDATING("인증 중"),
    NON_VALID("인증 실패");

    private final String value;

    ValidateState(String value){
        this.value = value;
    }
}
