package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum BasicSortType {
    NAME("이름순"),
    LATEST("최신순");

    private final String value;

    BasicSortType(String value) {
        this.value = value;
    }
}
