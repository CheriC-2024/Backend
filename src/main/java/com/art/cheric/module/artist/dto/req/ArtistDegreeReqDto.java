package com.art.cheric.module.artist.dto.req;


import com.art.cheric.global.validation.annotation.ValidYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Year;

@Schema(description = "작가 학력 DTO")
public record ArtistDegreeReqDto(
        @Schema(description = "학교 이름", example = "서울여자대학교")
        @Size(min = 2, max = 30, message = "학교 이름은 2자이상 30자 이하여야 합니다.")
        @NotBlank(message = "학교 이름은 필수 값입니다.")
        String schoolName,

        @Schema(description = "학교 전공", example = "디지털미디어학과")
        @Size(min = 2, max = 30, message = "전공 이름은 2자이상 30자 이하여야 합니다.")
        @NotBlank(message = "전공은 필수 값입니다.")
        String major,

        @Schema(description = "입학 년도", example = "2021")
        @ValidYear
        @NotNull(message = "입학 년도는 필수 값입니다.")
        Year entranceAt,

        @Schema(description = "졸업 년도", example = "2025")
        @ValidYear
        @NotNull(message = "졸업 년도는 필수 값입니다.")
        Year graduateAt

) {
}
