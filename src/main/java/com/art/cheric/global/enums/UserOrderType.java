package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum UserOrderType {
    NAME("이름순"),
    LATEST("최신순"),
    FOLLOWER("팔로워순");

    private final String value;

    UserOrderType(String value) {
        this.value = value;
    }
}
