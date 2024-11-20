package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum ChatGptType {
    THEME("테마 추출"),
    TITLE("제목 추출");


    private final String value;

    ChatGptType(String value) {
        this.value = value;
    }
}
