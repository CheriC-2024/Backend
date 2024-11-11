package com.art.cheric.global.enums;

import lombok.Getter;

@Getter
public enum FilePathType {
    USER_IMG("user", ValidateFileType.IMAGE),
    ARTIST_VALIDATE_FILE("artist-validation", ValidateFileType.DOCUMENT),
    ARTIST_ART_IMG("artist-art", ValidateFileType.IMAGE),
    OWN_ART_IMG("own-art", ValidateFileType.IMAGE),
    OWN_ART_VALIDATE_FILE("own-art-validation", ValidateFileType.DOCUMENT),
    EXHIBITION_IMG("exhibition-img", ValidateFileType.IMAGE),
    EXHIBITION_MUSIC("exhibition-music", ValidateFileType.AUDIO);

    private final String value;
    private final ValidateFileType fileType;

    FilePathType(String value, ValidateFileType fileType) {
        this.value = value;
        this.fileType = fileType;
    }
}
