package com.art.cheric.web_client.error;

import com.art.cheric.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum WebClientErrorCode implements ErrorCode {
    BLOCK_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "API 동기적 호출 중 오류 발생했습니다."),
    ASYNC_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "API 비동기적 호출 중 오류 발생했습니다."),
    DONT_HAVE_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API의 응답 결과가 존재하지 않습니다."),
    JSON_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API의 응답 JSON과 파싱에 실패했습니다."),
    PROCESS_ASYNC_RESPONSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API의 비동기 응답 처리에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    WebClientErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
