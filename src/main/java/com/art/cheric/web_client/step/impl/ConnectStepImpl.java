package com.art.cheric.web_client.step.impl;

import com.art.cheric.global.error.exception.WebClientException;
import com.art.cheric.web_client.step.ConnectStep;
import com.art.cheric.web_client.step.ResponseStep;
import com.art.cheric.web_client.error.WebClientErrorCode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Slf4j
public class ConnectStepImpl implements ConnectStep {

    private final WebClient.RequestHeadersSpec<?> methodType;

    // 동기 호출을 설정하고 요청 헤더와 응답 타입을 지정
    @Override
    public ResponseStep connectBlock(Map<String, String> headers, Class<?> responseType) {
        // 요청 헤더 설정
        headers.forEach(methodType::header);
        try {
            // 동기 호출을 수행하고 응답 반환
            Object response = methodType.retrieve().bodyToMono(responseType).block();
            return new ResponseStepImpl(response);
        } catch (Exception e) {
            log.error("[WebClient_ResponseStep] external api block connection error : {}", e.getMessage(), e);
            throw new WebClientException(WebClientErrorCode.BLOCK_CONNECTION_ERROR);
        }
    }

    // 비동기 호출을 설정하고 요청 헤더와 응답 타입을 지정
    @Override
    public ResponseStep connectAsync(Map<String, String> headers, Class<?> responseType) {
        // 요청 헤더 설정
        headers.forEach(methodType::header);
        try {
            // 비동기 호출을 수행하고 Mono로 응답 반환
            Mono<?> response = methodType.retrieve().bodyToMono(responseType);
            return new ResponseStepImpl(response);
        } catch (Exception e) {
            log.error("[WebClient_ResponseStep] external api async connection error : {}", e.getMessage(), e);
            throw new WebClientException(WebClientErrorCode.ASYNC_CONNECTION_ERROR);
        }
    }
}
