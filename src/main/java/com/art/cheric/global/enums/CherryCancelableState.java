package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum CherryCancelableState {
    CANCELED ("취소됨"),
    CANCELING("취소 중"),
    CANCELLABLE("취소 가능"),
    NON_CANCELLABLE("기간 지나 취소 불가"),
    USED("사용 완료");

    private final String value;

    CherryCancelableState(String value){
        this.value = value;
    }
}
