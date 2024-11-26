package com.art.cheric.module.art.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작품 정보 간단 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtBriefResDto {

    @Schema(description = "작품 id", example = "1")
    private final Long artId;

    @Schema(description = "작품 이미지 경로", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png")
    private final String imgUrl;

    @Schema(description = "작품 이름", example = "별이 빛나는 밤에")
    private final String name;

    @Schema(description = "컬렉터의 작품인지 여부", example = "true")
    private final boolean isCollectorsArt;

    @Schema(description = "체리 가격", example = "2")
    private final Integer cherryPrice;


    public static ArtBriefResDto of(Long artId, boolean isCollectorsArt, String imgUrl, Integer cherryPrice, String name) {
        return ArtBriefResDto.builder()
                .artId(artId)
                .isCollectorsArt(isCollectorsArt)
                .imgUrl(imgUrl)
                .cherryPrice(cherryPrice)
                .name(name)
                .build();
    }
}
