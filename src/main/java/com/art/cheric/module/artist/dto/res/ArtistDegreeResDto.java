package com.art.cheric.module.artist.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Year;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 학위 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistDegreeResDto {
    @Schema(description = "학교 이름", example = "서울여자대학교")
    private final String schoolName;

    @Schema(description = "학교 전공", example = "디지털미디어학과")
    private final String major;

    @Schema(description = "입학 년도", example = "2021")
    private final Year entranceAt;

    @Schema(description = "졸업 년도", example = "2025")
    private final Year graduateAt;

    public static ArtistDegreeResDto of(String schoolName, String major, Year entranceAt, Year graduateAt) {
        return ArtistDegreeResDto.builder()
                .schoolName(schoolName)
                .major(major)
                .entranceAt(entranceAt)
                .graduateAt(graduateAt)
                .build();
    }
}
