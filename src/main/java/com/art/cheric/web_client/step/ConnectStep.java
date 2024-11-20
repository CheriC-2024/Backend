package com.art.cheric.web_client.step;

import java.util.Map;

/**
 *  WebClient로 API 호출을 수행을 정의한 인터페이스
 * 동기 및 비동기 호출을 지원하며 헤더와 응답 타입을 설정할 수 있음
 */
public interface ConnectStep {
    ResponseStep connectBlock(Map<String, String> headers, Class<?> responseType);
    ResponseStep connectAsync(Map<String, String> headers, Class<?> responseType);
}