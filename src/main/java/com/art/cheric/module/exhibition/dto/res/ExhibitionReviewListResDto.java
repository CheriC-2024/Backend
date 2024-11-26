package com.art.cheric.module.exhibition.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 댓글 리스트 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionReviewListResDto {

    @Schema(description = "댓글 id", example = "1")
    private final Long id;

    @Schema(description = "댓글", example = "이 전시를 보며 별들 속에 있는 것 같은 기분이었습니다.")
    private final String review;

    @Schema(description = "댓글 작성자 이름", example = "체리시")
    private final String name;

    @Schema(description = "좋아요 수", example = "12")
    private final int heartCount;

    @Schema(description = "대댓글 수", example = "2")
    private final int replyCount;

    @Schema(description = "댓글 작성 시간", example = "2024-12-23")
    private final String createAt;


    public static ExhibitionReviewListResDto of(Long id, String review, String name, int heartCount, int replyCount,
                                                String createAt) {
        return ExhibitionReviewListResDto.builder()
                .id(id)
                .review(review)
                .name(name)
                .heartCount(heartCount)
                .replyCount(replyCount)
                .createAt(createAt)
                .build();
    }

}
