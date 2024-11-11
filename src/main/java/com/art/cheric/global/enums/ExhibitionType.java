package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ExhibitionType {
    INDIVIDUAL("개인전"),
    GROUP("단체전");

    private final String value;

    ExhibitionType(String value){
        this.value = value;
    }
}
