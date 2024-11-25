package com.art.cheric.module.exhibition.dto.res;

import com.art.cheric.global.enums.FontColorType;
import com.art.cheric.global.enums.FontType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 기본 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionBasicResDto {
    @Schema(description = "전시 id", example = "1")
    private final Long exhibitionId;

    @Schema(description = "전시 제목", example = "별빛:하늘을 그리다")
    private final String name;

    @Schema(description = "전시 폰트", example = "BASIC")
    private final FontType font;

    @Schema(description = "전시 폰트 색상", example = "BLACK")
    private final FontColorType fontColor;

    @Schema(description = "전시 커버 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/EXHIBITION_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-exhibitionImage.png")
    private final String coverImgUrl;

    @Schema(description = "전시 배경 음악", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/EXHIBITION_MUSIC/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-exhibitionMusic.png")
    private final String musicUrl;

    @Schema(description = "전시 하트 수", example = "120")
    private final int heartCount;

    @Schema(description = "전시 관람 수", example = "123")
    private final int hits;

    public static ExhibitionBasicResDto of(Long exhibitionId, String name, FontType font, FontColorType fontColor,
                                           String coverImgUrl, String musicUrl, int heartCount, int hits) {
        return ExhibitionBasicResDto.builder()
                .exhibitionId(exhibitionId)
                .name(name)
                .font(font)
                .fontColor(fontColor)
                .coverImgUrl(coverImgUrl)
                .musicUrl(musicUrl)
                .heartCount(heartCount)
                .hits(hits)
                .build();
    }
}
