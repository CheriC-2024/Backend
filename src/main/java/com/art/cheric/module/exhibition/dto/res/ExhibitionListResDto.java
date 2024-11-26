package com.art.cheric.module.exhibition.dto.res;

import com.art.cheric.global.enums.ExhibitionBackgroundType;
import com.art.cheric.global.enums.FontColorType;
import com.art.cheric.global.enums.FontType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "전시 리스트 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExhibitionListResDto {
    @Schema(description = "전시 id", example = "1")
    private final Long exhibitionId;

    @Schema(description = "전시 제목", example = "별빛:하늘을 그리다")
    private final String name;

    @Schema(description = "전시 폰트", example = "BASIC")
    private final FontType font;

    @Schema(description = "전시 폰트 색상", example = "BLACK")
    private final FontColorType fontColor;

    @Schema(description = "색상", example = "[\"#CF3420\", \"#CF3421\", \"#CF3422\", \"#CF3423\"]")
    private final List<String> colors;

    @Schema(description = "전시 색상 배경일 경우 그라데이션 방향", example = "TOP_DOWN")
    private final ExhibitionBackgroundType exhibitionBackgroundType;

    @Schema(description = "전시 커버 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/EXHIBITION_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-exhibitionImage.png")
    private final String coverImgUrl;

    @Schema(description = "테마", example = "[\"하늘\", \"달\", \"별\"]")
    private final List<String> themes;

    @Schema(description = "전시 하트 수", example = "120")
    private final int heartCount;

    @Schema(description = "전시 관람 수", example = "123")
    private final int hits;

    public static ExhibitionListResDto of(Long exhibitionId, String name, FontType font, FontColorType fontColor,
                                          List<String> colors, ExhibitionBackgroundType exhibitionBackgroundType,
                                          String coverImgUrl, List<String> themes, int heartCount, int hits) {
        return ExhibitionListResDto.builder()
                .exhibitionId(exhibitionId)
                .name(name)
                .font(font)
                .fontColor(fontColor)
                .coverImgUrl(coverImgUrl)
                .colors(colors)
                .exhibitionBackgroundType(exhibitionBackgroundType)
                .themes(themes)
                .heartCount(heartCount)
                .hits(hits)
                .build();
    }
}
