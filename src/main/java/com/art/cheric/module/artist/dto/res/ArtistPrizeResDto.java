package com.art.cheric.module.artist.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Year;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 수상처 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistPrizeResDto {
    @Schema(description = "수상처", example = "AI SPARK 창업 경진대회")
    private final String organization;

    @Schema(description = "수상명", example = "장려상")
    private final String level;

    @Schema(description = "수상 년도", example = "2024")
    private final Year receivedAt;

    public static ArtistPrizeResDto of(String organization, String level, Year receivedAt) {
        return ArtistPrizeResDto.builder()
                .organization(organization)
                .level(level)
                .receivedAt(receivedAt)
                .build();
    }
}
