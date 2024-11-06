package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum FontType {
    BASIC ("기본 시스템 폰트"),
    BLACK_HAN_SANS ("맑은 고딕"),
    MAPODACAPO ("마포다카포"),
    SF_HAMBAKSNOW  ("SF함박눈"),
    HS_SANTOKKI ("HS산토끼"),
    LOTTERIA_CHAB ("롯데리아 찹땅겨"),
    OWNGLYPH_RYURUE ("온글잎류류");

    private final String value;

    FontType(String value){
        this.value = value;
    }
}
