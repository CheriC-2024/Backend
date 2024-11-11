package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum FontColorType {
    BLACK ("검은색"),
    WHITE ("하얀색");

    private final String value;

    FontColorType(String value){
        this.value = value;
    }
}
