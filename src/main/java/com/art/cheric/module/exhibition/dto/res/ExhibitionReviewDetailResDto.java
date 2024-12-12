package com.art.cheric.module.exhibition.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 댓글 상세 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionReviewDetailResDto {

    @Schema(description = "댓글 id", example = "1")
    private final Long id;

    @Schema(description = "댓글", example = "이 전시를 보며 별들 속에 있는 것 같은 기분이었습니다.")
    private final String review;

    @Schema(description = "댓글 작성자 이름", example = "체리시")
    private final String name;

    @Schema(description = "댓글 작성자 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
    private final String imgUrl;

    @Schema(description = "좋아요 수", example = "12")
    private final int heartCount;

    @Schema(description = "댓글 작성 시간", example = "2024-12-23")
    private final String createAt;

    @Schema(description = "대댓글 정보 DTO 리스트")
    private final List<ExhibitionReviewDetailResDto> replies = new ArrayList<>();

    @Schema(description = "좋아요 여부", example = "true")
    private final Boolean isHeart;

    public static ExhibitionReviewDetailResDto of(Long id, String review, String name, String imgUrl, int heartCount,
                                                  String createAt, Boolean isHeart) {
        return ExhibitionReviewDetailResDto.builder()
                .id(id)
                .review(review)
                .name(name)
                .imgUrl(imgUrl)
                .heartCount(heartCount)
                .createAt(createAt)
                .isHeart(isHeart)
                .build();
    }

    public ExhibitionReviewDetailResDto addExhibitionReviewListResDto(List<ExhibitionReviewDetailResDto> replies) {
        this.replies.addAll(replies);
        return this;
    }
}
