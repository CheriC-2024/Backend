package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ArtType {
    PAINTING("회화"),
    OIL_PAINTING("유화"),
    WATER_PAINTING("수채화"),
    WESTERN_PAINTING("서양화"),
    ORIENTAL_PAINTING("동양화"),
    DIGITAL_ART("디지털아트"),
    POP_ART("팝아트");

    private final String value;

    ArtType(String value) {
        this.value = value;
    }
}
