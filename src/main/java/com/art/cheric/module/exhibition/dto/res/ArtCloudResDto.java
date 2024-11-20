package com.art.cheric.module.exhibition.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Cloud Vision 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtCloudResDto {
    @Schema(description = "작품 id", example = "1")
    private final Long artId;

    @Schema(description = "작품에서 추출된 특성들, 색상이라면 색상 추출 / 라벨이라면 라벨 추출", example = "[\"#123234\",\"#243253\"]")
    private final List<String> properties;

    public static ArtCloudResDto of(Long artId, List<String> properties) {
        return ArtCloudResDto.builder()
                .artId(artId)
                .properties(properties)
                .build();
    }
}
