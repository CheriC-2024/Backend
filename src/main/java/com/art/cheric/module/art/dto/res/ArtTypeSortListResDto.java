package com.art.cheric.module.art.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "분야 작품 정보 리스트 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtTypeSortListResDto {

    @Schema(description = "작품 분야", example = "회화")
    private final String artType;

    @Schema(description = "사용자 선호 분야인지 여부", example = "true")
    private final boolean isUserPreference;

    @Schema(description = "작품 정보")
    private final List<ArtMostBriefListResDto> artMostBriefListRess;

    public static ArtTypeSortListResDto of(String artType, boolean isUserPreference,
                                           List<ArtMostBriefListResDto> artMostBriefListRess) {
        return ArtTypeSortListResDto.builder()
                .artType(artType)
                .isUserPreference(isUserPreference)
                .artMostBriefListRess(artMostBriefListRess)
                .build();
    }
}
