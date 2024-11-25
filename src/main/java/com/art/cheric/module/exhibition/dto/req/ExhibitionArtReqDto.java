package com.art.cheric.module.exhibition.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "전시 작품 DTO")
public record ExhibitionArtReqDto(
        @Schema(description = "작품 소개글", example = "이러쿵 저러쿵을 통해 수집하게 되었습니다.")
        @NotBlank
        @Size(max = 500, message = "전시 작품 소개글 500자 이하로 작성해야 합니다.")
        String description,

        @Schema(description = "작품 수집 계기", example = "별빛을 나타내는 게 마음에 와닿아서 수집하게 되었습니다.")
        @NotBlank
        @Size(max = 500, message = "작품 수집 계기는 500자 이하로 작성해야 합니다.")
        String reasonForPurchase,

        @Schema(description = "작품 감상평", example = "별빛을 나타내는 작품을 보는 과정에서 행복했습니다.")
        @NotBlank
        @Size(max = 500, message = "작품 감상평은 500자 이하로 작성해야 합니다.")
        String review,

        @Schema(description = "전시하는 작품 id", example = "1")
        @NotNull
        Long artId
) {
}
