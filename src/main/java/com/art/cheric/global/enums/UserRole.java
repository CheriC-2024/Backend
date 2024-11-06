package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    COLLECTOR("컬렉터"),
    ARTIST("아티스트"),
    ADMIN("관리자");

    private final String value;

    UserRole(String value){
        this.value = value;
    }
}
