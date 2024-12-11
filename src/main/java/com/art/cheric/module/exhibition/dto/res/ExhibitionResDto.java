package com.art.cheric.module.exhibition.dto.res;

import com.art.cheric.module.user.dto.res.ExhibitionUserResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 보기 기본 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionResDto {
    @Schema(description = "전시 설명", example = "별빛을 나타내는 작품을 모은 전시입니다.")
    private final String description;

    @Schema(description = "전시 하트 수", example = "1")
    private final int heartCount;

    @Schema(description = "전시 관람 수", example = "1")
    private final int hits;

    @Schema(description = "전시 작품 정보 리스트")
    private final List<ExhibitionArtResDto> exhibitionArtRess;

    @Schema(description = "전시 등록자 정보 DTO")
    private final ExhibitionUserResDto userRes;

    @Schema(description = "전시 댓글 1위 정보 DTO")
    private final ExhibitionReviewResDto exhibitionReviewRes;

    @Schema(description = "좋아요 여부", example = "true")
    private final Boolean isHeart;

    public static ExhibitionResDto of(String description, int heartCount, int hits,
                                      List<ExhibitionArtResDto> exhibitionArtRess, ExhibitionUserResDto userRes,
                                      ExhibitionReviewResDto exhibitionReviewRes, Boolean isHeart) {
        return ExhibitionResDto.builder()
                .description(description)
                .heartCount(heartCount)
                .hits(hits)
                .exhibitionArtRess(exhibitionArtRess)
                .userRes(userRes)
                .exhibitionReviewRes(exhibitionReviewRes)
                .isHeart(isHeart)
                .build();
    }
}
