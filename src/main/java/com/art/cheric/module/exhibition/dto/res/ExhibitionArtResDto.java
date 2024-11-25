package com.art.cheric.module.exhibition.dto.res;

import com.art.cheric.module.art.dto.res.ArtExhibitionResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 작품 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionArtResDto {

    @Schema(description = "작품 소개글", example = "이러쿵 저러쿵을 통해 수집하게 되었습니다.")
    private final String description;

    @Schema(description = "작품 수집 계기", example = "별빛을 나타내는 게 마음에 와닿아서 수집하게 되었습니다.")
    private final String reasonForPurchase;

    @Schema(description = "작품 감상평", example = "별빛을 나타내는 작품을 보는 과정에서 행복했습니다.")
    private final String review;

    @Schema(description = "작품 기본 정보")
    private final ArtExhibitionResDto artExhibitionRes;

    public static ExhibitionArtResDto of(String description, String reasonForPurchase, String review,
                                         ArtExhibitionResDto artExhibitionRes) {
        return ExhibitionArtResDto.builder()
                .description(description)
                .reasonForPurchase(reasonForPurchase)
                .review(review)
                .artExhibitionRes(artExhibitionRes)
                .build();
    }
}
