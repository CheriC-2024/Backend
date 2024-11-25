package com.art.cheric.module.art.dto.res;

import com.art.cheric.global.enums.ArtType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Year;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 작품 정보 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtExhibitionResDto {

    @Schema(description = "컬렉터의 작품인지 여부", example = "true")
    private final boolean isCollectorsArt;

    @Schema(description = "작품 이미지 경로", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png")
    private final String imgUrl;

    @Schema(description = "체리 가격", example = "2")
    private final Integer cherryPrice;

    @Schema(description = "작품 이름", example = "별이 빛나는 밤에")
    private final String name;

    @Schema(description = "작가 이름", example = "작가 작품인 경우, 해당 작가 이름 / 소장 작품인 경우 저작권자 이름")
    private final String artistName;

    @Schema(description = "시리즈", example = "별")
    private final String series;

    @Schema(description = "가로 사이즈", example = "10000")
    private final int horizontalSize;

    @Schema(description = "세로 사이즈", example = "20000")
    private final int verticalSize;

    @Schema(description = "작품 재로", example = "수채화 물감을 사용하였습니다.")
    private final String material;

    @Schema(description = "작품 제작년도", example = "2024")
    private final Year madeAt;

    @Schema(description = "작품 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
    private final List<ArtType> artTypes;

    @Schema(description = "소장 작품 관련 추가 정보 DTO", example = "작가 작품인 경우 나오지 않음")
    private final OwnArtResDto ownArtRes;

    @Schema(description = "하트 개수", example = "1")
    private final int heartCount;

    public static ArtExhibitionResDto of(boolean isCollectorsArt, String imgUrl, Integer cherryPrice, String name,
                                         String artistName, String series, int horizontalSize,
                                         int verticalSize, String material, Year madeAt, List<ArtType> artTypes,
                                         OwnArtResDto ownArtRes, int heartCount) {
        return ArtExhibitionResDto.builder()
                .isCollectorsArt(isCollectorsArt)
                .imgUrl(imgUrl)
                .cherryPrice(cherryPrice)
                .name(name)
                .artistName(artistName)
                .series(series)
                .horizontalSize(horizontalSize)
                .verticalSize(verticalSize)
                .material(material)
                .madeAt(madeAt)
                .artTypes(artTypes)
                .ownArtRes(ownArtRes)
                .heartCount(heartCount)
                .build();
    }
}
