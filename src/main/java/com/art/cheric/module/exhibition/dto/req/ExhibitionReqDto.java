package com.art.cheric.module.exhibition.dto.req;

import com.art.cheric.global.enums.ExhibitionBackgroundType;
import com.art.cheric.global.enums.FontColorType;
import com.art.cheric.global.enums.FontType;
import com.art.cheric.global.validation.annotation.UniqueElements;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "전시 생성 요청 DTO")
public record ExhibitionReqDto(
        @Schema(description = "전시 제목", example = "별빛:하늘을 그리다")
        @NotBlank
        @Size(max = 30, message = "전시 제목은 30자 이하로 작성해야 합니다.")
        String name,

        @Schema(description = "전시 설명", example = "별빛을 나타내는 작품을 모은 전시입니다.")
        @NotBlank
        @Size(max = 500, message = "전시 설명은 500자 이하로 작성해야 합니다.")
        String description,

        @Schema(description = "전시 폰트", example = "BASIC")
        @NotNull
        @Enumerated(EnumType.STRING)
        FontType font,

        @Schema(description = "전시 폰트 색상", example = "BLACK")
        @NotNull
        @Enumerated(EnumType.STRING)
        FontColorType fontColor,

        @Schema(description = "전시 커버 이미지 > 해당 값이 색상 보다 우선 처리 됨(색상 처리 시, null로 전달 필요)", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/EXHIBITION_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-exhibitionImage.png")
        String coverImgUrl,

        @Schema(description = "색상", example = "[\"#CF3420\", \"#CF3421\", \"#CF3422\", \"#CF3423\"]")
        @UniqueElements
        @Size(min = 4, max = 4, message = "색상 4개만 설정할 수 있습니다.")
        List<String> colors,

        @Schema(description = "전시 색상 배경일 경우 그라데이션 방향", example = "TOP_DOWN")
        @Enumerated(EnumType.STRING)
        ExhibitionBackgroundType exhibitionBackgroundType,

        @Schema(description = "전시 배경 음악", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/EXHIBITION_MUSIC/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-exhibitionMusic.png")
        String musicUrl,

        @Schema(description = "테마", example = "[\"별\", \"하늘\", \"빛\"]")
        @NotEmpty
        @UniqueElements
        @Size(max = 3, message = "테마는 최대 3개까지만 설정할 수 있습니다.")
        List<String> themes,

        @Schema(description = "전시 작품 정보 리스트")
        @NotEmpty
        @Size(max = 30, message = "작품은 최대 30개까지만 설정할 수 있습니다.")
        List<ExhibitionArtReqDto> exhibitionArtReqs
) {
}
