package com.art.cheric.global.enums;

import java.util.List;
import lombok.Getter;

@Getter
public enum ValidateFileType {
    IMAGE(List.of("jpg", "jpeg", "png")),
    AUDIO(List.of("mp3")),
    DOCUMENT(List.of("pdf", "doc", "docx", "xls", "xlsx"));

    private final List<String> extensions;

    ValidateFileType(List<String> extensions) {
        this.extensions = extensions;
    }
}
