package com.art.cheric.module.art.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작품 분야별 정보 간단 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtMostBriefListResDto {

    @Schema(description = "작품 id", example = "1")
    private final Long artId;

    @Schema(description = "작품 이미지 경로", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png")
    private final String imgUrl;

    public static ArtMostBriefListResDto of(Long artId, String imgUrl) {
        return ArtMostBriefListResDto.builder()
                .artId(artId)
                .imgUrl(imgUrl)
                .build();
    }
}
