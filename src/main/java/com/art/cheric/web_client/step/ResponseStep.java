package com.art.cheric.web_client.step;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 외부 API의 응답을 다양한 형태로 반환하는 인터페이스
 * JSON, 객체 또는 void 타입으로 반환 가능
 */
public interface ResponseStep {
    Object toObjectCall();
    JsonNode toJsonNodeCall();
    void toVoidCall();
}
