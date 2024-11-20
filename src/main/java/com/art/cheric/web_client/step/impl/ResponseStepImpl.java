package com.art.cheric.web_client.step.impl;

import com.art.cheric.global.error.exception.WebClientException;
import com.art.cheric.web_client.step.ResponseStep;
import com.art.cheric.web_client.error.WebClientErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Slf4j
public class ResponseStepImpl implements ResponseStep {

    private final Object response;
    private final Mono<?> responseMono;
    private final ObjectMapper objectMapper = new ObjectMapper();

    //  동기 응답 생성자
    public ResponseStepImpl(Object response) {
        this(response, null);
    }

    // 비동기 응답 생성자
    public ResponseStepImpl(Mono<?> responseMono) {
        this(null, responseMono);
    }

    // 동기 또는 비동기 결과를 객체로 변환하여 반환
    @Override
    public Object toObjectCall() {
        return Optional.ofNullable(response)
                .orElseGet(this::handleAsyncResponse);
    }

    // 동기 또는 비동기 결과를  JSON 노드로 변환하여 반환
    @Override
    public JsonNode toJsonNodeCall() {
        Object result = toObjectCall();
        if (result == null) {
            throw new WebClientException(WebClientErrorCode.DONT_HAVE_RESPONSE);
        }
        try {
            return objectMapper.readTree(result.toString());
        } catch (JsonProcessingException e) {
            log.error("[WebClient_ResponseStep] json parsing failed : {}", e.getMessage());
            throw new WebClientException(WebClientErrorCode.JSON_PARSING_FAILED);
        }
    }

    // 비동기 요청을 실행하고 결과를 사용하지 않는 경우에 호출
    @Override
    public void toVoidCall() {
        Optional.ofNullable(responseMono)
                .ifPresent(Mono::subscribe);
    }

    // 비동기 Mono 객체에서 동기적으로 결과를 가져오기
    private Object handleAsyncResponse() {
        if (responseMono == null) {
            return null;
        }
        try {
            return responseMono.toFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("[WebClient_ResponseStep] processing async response failed : {}", e.getMessage());
            throw new WebClientException(WebClientErrorCode.PROCESS_ASYNC_RESPONSE_FAILED);
        }
    }
}
