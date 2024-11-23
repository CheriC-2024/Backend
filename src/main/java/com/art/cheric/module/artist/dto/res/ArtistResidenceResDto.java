package com.art.cheric.module.artist.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 레지던시 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistResidenceResDto {
    @Schema(description = "레지던시명", example = "00아트플랫폼")
    private final String residenceName;

    public static ArtistResidenceResDto of(String residenceName) {
        return ArtistResidenceResDto.builder()
                .residenceName(residenceName)
                .build();
    }
}
