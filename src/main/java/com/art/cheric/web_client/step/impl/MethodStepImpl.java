package com.art.cheric.web_client.step.impl;

import com.art.cheric.web_client.step.ConnectStep;
import com.art.cheric.web_client.step.MethodStep;
import io.netty.channel.ChannelOption;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
public class MethodStepImpl<T> implements MethodStep<T> {

    private final WebClient.Builder webClientBuilder;
    private WebClient.RequestHeadersSpec<?> methodType;


    @Override
    public ConnectStep post(String baseUrl, T requestBody) {
        // 기본 URL을 설정하고 POST 메서드로 요청 본문을 설정
        this.methodType = this.setBaseUrl(!StringUtils.hasText(baseUrl) ? "" : baseUrl)
                .post()  // POST 메서드 설정
                .bodyValue(requestBody);  // 요청 본문으로 requestBody 객체를 전달
        return new ConnectStepImpl(this.methodType);
    }


    @Override
    public ConnectStep post(String baseUrl, String path, T requestBody) {
        // URL과 Path를 설정하고 POST 요청 본문을 추가
        this.methodType = this.setBaseUrl(!StringUtils.hasText(baseUrl) ? "" : baseUrl)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .build())
                .bodyValue(requestBody);
        return new ConnectStepImpl(this.methodType);
    }


    @Override
    public ConnectStep post(String baseUrl, MultiValueMap<String, String> params, T requestBody) {
        // queryParam을 사용하여 POST 요청 설정
        this.methodType = this.setBaseUrl(baseUrl)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(params)
                        .build())
                .bodyValue(requestBody);
        return new ConnectStepImpl(this.methodType);
    }


    @Override
    public ConnectStep get(String baseUrl, MultiValueMap<String, String> params) {
        // GET 요청 설정, queryParams가 없을 경우 빈 LinkedMultiValueMap 사용
        this.methodType = this.setBaseUrl(baseUrl)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParams(params == null || params.isEmpty() ? new LinkedMultiValueMap<>() : params)
                        .build());
        return new ConnectStepImpl(this.methodType);
    }

    @Override
    public ConnectStep get(String baseUrl, String path, MultiValueMap<String, String> params) {
        // URL, Path, queryParams를 설정하여 GET 요청
        this.methodType = this.setBaseUrl(baseUrl)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParams(params == null || params.isEmpty() ? new LinkedMultiValueMap<>() : params)
                        .build());
        return new ConnectStepImpl(this.methodType);
    }


    private WebClient setBaseUrl(String baseUrl) {
        // 기본 URI 빌더 팩토리를 생성하고 인코딩 모드를 설정
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        // 메모리 크기 제한 설정
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(50 * 1024 * 1024))  // 최대 메모리 크기 설정 (50MB)
                .build();

        // HTTP 커넥터 설정
        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120000)  // 연결 타임아웃 설정 (120초)
                        .responseTimeout(Duration.ofSeconds(120)));  // 응답 타임아웃 설정 (120초)

        // WebClient 빌더에 설정 적용
        return this.webClientBuilder
                .exchangeStrategies(exchangeStrategies)
                .clientConnector(httpConnector)
                .uriBuilderFactory(factory)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(baseUrl)
                .build();
    }
}
