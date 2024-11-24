package com.art.cheric.module.artist.dto.req;


import com.art.cheric.global.validation.annotation.ValidYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Year;

@Schema(description = "작가 수상 DTO")
public record ArtistPrizeReqDto(
        @Schema(description = "수상처", example = "AI SPARK 창업 경진대회")
        @NotBlank(message = "수상처는 필수 값입니다.")
        @Size(min = 2, max = 30, message = "수상처는 2자이상 30자 이하여야 합니다.")
        String organization,

        @Schema(description = "수상명", example = "장려상")
        @NotBlank(message = "수상명은 필수 값입니다.")
        @Size(min = 2, max = 30, message = "수상명은 2자이상 30자 이하여야 합니다.")
        String level,

        @Schema(description = "수상 년도", example = "2024")
        @ValidYear
        @NotNull(message = "수상 년도는 필수 값입니다.")
        Year receivedAt

) {
}
