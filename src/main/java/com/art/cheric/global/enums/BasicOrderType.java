package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum BasicOrderType {
    NAME("이름순"),
    LATEST("최신순");

    private final String value;

    BasicOrderType(String value) {
        this.value = value;
    }
}
