package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ExhibitionOrderType {
    NAME("이름순"),
    LATEST("최신순"),
    LIKE("좋아요순"),
    HITS("조회수순");

    private final String value;

    ExhibitionOrderType(String value) {
        this.value = value;
    }
}
