package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum AlarmType {
    ARTIST("작가"),
    ARTIST_ART("작가 작품"),
    OWN_ART("소장 작품"),
    EXHIBITION("전시"),
    EXHIBITION_REVIEW("전시 리뷰"),
    CHERRY("체리"),
    FOLLOW("팔로우"),
    EVENT("이벤트"),
    NOTICE("공지");

    private final String value;

    AlarmType(String value) {
        this.value = value;
    }
}
