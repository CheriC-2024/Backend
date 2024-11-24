package com.art.cheric.module.art.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "소장 작품 소개 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtDescriptionResDto {

    @Schema(description = "작품 id", example = "1")
    private final Long artId;

    @Schema(description = "작품 소개", example = "해당 작품은 이래서 소장하게 되었습니다.")
    private final String description;


    public static ArtDescriptionResDto of(Long artId, String description) {
        return ArtDescriptionResDto.builder()
                .artId(artId)
                .description(description)
                .build();
    }
}
