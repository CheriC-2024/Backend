package com.art.cheric.web_client.step;


import org.springframework.util.MultiValueMap;

/**
 * WebClient를 통해 HTTP 메서드를 설정하고 URI 및 파라미터를 추가하는 인터페이스
 * POST와 GET 메서드를 지원
 */
public interface MethodStep<T> {
    ConnectStep post(String baseUrl, T requestBody);
    ConnectStep post(String baseUrl, String path, T requestBody);
    ConnectStep post(String baseUrl, MultiValueMap<String, String> params, T requestBody);
    ConnectStep get(String baseUrl, MultiValueMap<String, String> params);
    ConnectStep get(String baseUrl, String path, MultiValueMap<String, String> params);
}
