package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ArtOrderType {
    NAME("이름순"),
    LATEST("최신순"),
    LIKE("좋아요순");

    private final String value;

    ArtOrderType(String value) {
        this.value = value;
    }
}
