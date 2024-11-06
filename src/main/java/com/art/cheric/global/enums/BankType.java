package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum BankType {
    KB("국민"),
    HANA("하나"),
    KAKAO("키카오"),
    OURI("우리"),
    TOSS("토스"),
    SHINHAN("신한");

    private final String value;

    BankType(String value){
        this.value = value;
    }
}
