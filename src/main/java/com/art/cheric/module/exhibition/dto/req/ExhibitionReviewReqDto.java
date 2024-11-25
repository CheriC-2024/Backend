package com.art.cheric.module.exhibition.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "전시 댓글 요청 DTO")
public record ExhibitionReviewReqDto(
        @Schema(description = "댓글 내용", example = "수집하신 소장 작품 너무 좋네요!")
        @NotBlank
        @Size(max = 150, message = "전시 댓글은 150자 이하로 작성해야 합니다.")
        String message
) {
}
