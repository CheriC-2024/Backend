package com.art.cheric.global.util;

import com.art.cheric.global.error.GlobalErrorCode;
import com.art.cheric.global.error.exception.AppException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonResponseUtil {
    private final ObjectMapper objectMapper;

    // string에서 json 파싱하기 위한 JsonNode 추출
    public JsonNode parseJson(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            log.error("[Chat GPT] Failed to parse API response: {}", e.getMessage(), e);
            throw new AppException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // stirng에서 json의 특정 필드 추출
    public List<String> extractStringList(JsonNode parentNode, String fieldName) {
        if (parentNode == null || !parentNode.has(fieldName)) {
            return Collections.emptyList(); // 필드가 없으면 빈 리스트 반환
        }

        JsonNode fieldNode = parentNode.get(fieldName);
        if (!fieldNode.isArray()) {
            return Collections.emptyList(); // 필드가 배열 형태가 아니면 빈 리스트 반환
        }

        List<String> result = new ArrayList<>();
        for (JsonNode node : fieldNode) {
            result.add(node.asText()); // 배열 내부 요소를 문자열로 변환하여 추가
        }
        return result;
    }
}
