package com.art.cheric.module.collection.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CollectionErrorCode implements ErrorCode {
    ART_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "작품 id 리스트는 필수 값입니다."),
    COLLECTION_NAME_DUPLICATE(HttpStatus.CONFLICT, "해당 이름을 가진 컬렉션이 이미 있습니다."),
    COLLECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 컬렉션을 찾을 수 없습니다."),
    COLLECTION_ART_DUPLICATED(HttpStatus.CONFLICT, "컬렉션 내에 똑같은 작품이 들어갈 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    CollectionErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
