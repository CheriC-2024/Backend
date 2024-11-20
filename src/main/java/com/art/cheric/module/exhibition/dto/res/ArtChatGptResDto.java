package com.art.cheric.module.exhibition.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Chat Gpt 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtChatGptResDto {
    @Schema(description = "추출 결과", example = "[\"전시 테마\",\"전시 테마\"]")
    @JsonProperty("result")
    private List<String> result;

    @Schema(description = "추출 이유", example = "[\"전시 테마 추출 이유\",\"전시 테마 추출 이유\"]")
    @JsonProperty("reason")
    private List<String> reason;

    public static ArtChatGptResDto of(List<String> result, List<String> reason) {
        return ArtChatGptResDto.builder()
                .result(result)
                .reason(reason)
                .build();
    }
}
