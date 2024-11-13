package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ArtType {
    PAINTING("회화"),
    ORIENTAL_PAINTING("동양화"),
    OIL_PAINTING("유화"),
    WATER_PAINTING("수채화"),
    PRINTMAKING_PAINTING ("판화"),
    NEW_MEDIA_ART ("뉴미디어"),
    DRAWING_ART  ("드로잉"),
    DESIGN_ART ("디자인");

    private final String value;

    ArtType(String value) {
        this.value = value;
    }
}
