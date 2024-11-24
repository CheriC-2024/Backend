package com.art.cheric.module.artist.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 소장처 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistArtStorageResDto {
    @Schema(description = "소장처", example = "서울시립미술 소장관")
    private final String location;

    public static ArtistArtStorageResDto of(String location) {
        return ArtistArtStorageResDto.builder()
                .location(location)
                .build();
    }
}
