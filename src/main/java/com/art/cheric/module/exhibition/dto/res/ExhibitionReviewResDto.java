package com.art.cheric.module.exhibition.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 좋아요 1위 댓글 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionReviewResDto {

    @Schema(description = "댓글", example = "이 전시를 보며 별들 속에 있는 것 같은 기분이었습니다.")
    private final String review;

    @Schema(description = "댓글 작성자 이름", example = "체리시")
    private final String name;


    public static ExhibitionReviewResDto of(String review, String name) {
        return ExhibitionReviewResDto.builder()
                .review(review)
                .name(name)
                .build();
    }
}
