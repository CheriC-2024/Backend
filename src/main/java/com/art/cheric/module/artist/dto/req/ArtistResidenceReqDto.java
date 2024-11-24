package com.art.cheric.module.artist.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "작가 레지던시 DTO")
public record ArtistResidenceReqDto(
        @Schema(description = "레지던시명", example = "00아트플랫폼")
        @NotBlank(message = "레지던시명은 필수 값입니다.")
        @Size(min = 2, max = 30, message = "레지던시명은 2자이상 30자 이하여야 합니다.")
        String residenceName
) {
}
