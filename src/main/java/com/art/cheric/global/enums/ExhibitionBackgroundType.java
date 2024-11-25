package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ExhibitionBackgroundType {
    TOP_DOWN("위에서 아래"),
    DOWN_TOP("아래서 위"),
    LEFT_CORNER_RIGHT_CORNER("왼쪽에서 오른쪽 대각선"),
    RIGHT_CORNER_LEFT_CORNER ("오른쪽에서 왼쪽 대각선");

    private final String value;

    ExhibitionBackgroundType(String value) {
        this.value = value;
    }
}
