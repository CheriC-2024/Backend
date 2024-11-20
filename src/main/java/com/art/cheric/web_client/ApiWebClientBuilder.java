package com.art.cheric.web_client;

import com.art.cheric.web_client.step.MethodStep;
import com.art.cheric.web_client.step.impl.MethodStepImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 외부 API 요청의 시작점을 제공하는 클래스
 *  WebClient 요청을 위한 초기 설정을 담당
 */
@Component
@RequiredArgsConstructor
public class ApiWebClientBuilder {

    private final WebClient.Builder webClientBuilder;

    //요청 설정의 시작 단계로 이동
    public <T> MethodStep<T> request() {
        return new MethodStepImpl<>(this.webClientBuilder);
    }
}
