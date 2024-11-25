package com.art.cheric.module.art.dto.req;


import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.validation.annotation.UniqueElements;
import com.art.cheric.global.validation.annotation.ValidYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Year;
import java.util.List;

@Schema(description = "작품 저장 요청 DTO")
public record ArtReqDto(

        @Schema(description = "작품 이름", example = "별이 빛나는 밤에")
        @NotBlank
        @Size(max = 50, message = "작품 이름은 50자를 넘을 수 없습니다.")
        String name,

        @Schema(description = "작품 설명", example = "고흐가 그렸던 별이 빛나는 밤에 라는 작품입니다.")
        @NotBlank
        @Size(max = 300, message = "작품 설명은 300자를 넘을 수 없습니다.")
        String description,

        @Schema(description = "작품 시리즈", example = "별")
        @Size(max = 30, message = "작품 시리즈는 30자를 넘을 수 없습니다.")
        String series,

        @Schema(description = "작품 재료", example = "수채화 물감을 사용했습니다.")
        @NotBlank
        @Size(max = 50, message = "작품 재료는 50자를 넘을 수 없습니다.")
        String material,

        @Schema(description = "작품 만들어진 년도", example = "2024")
        @ValidYear
        @NotNull
        Year madeAt,

        @Schema(description = "작품의 체리 가격", example = "2")
        @Max(value = 100, message = "작품의 체리 가격은 체리 100개를 초과할 수 없습니다.")
        Integer cherryPrice,

        @Schema(description = "작품 가로 사이즈", example = "100000")
        @NotNull
        @Min(value = 1, message = "작품 가로 사이즈는 1보다 커야 합니다.")
        int horizontalSize,

        @Schema(description = "작품 세로 사이즈", example = "200000")
        @NotNull
        @Min(value = 1, message = "작품 세로 사이즈는 1보다 커야 합니다.")
        int verticalSize,

        @Schema(description = "작품 메인 이미지 경로", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png")
        @NotBlank
        String imgUrl,

        @Schema(description = "작품 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
        @NotEmpty(message = "작품 분야는 필수 값입니다.")
        @UniqueElements
        @Size(max = 2, message = "작품 분야는 2개까지 입력 가능합니다.")
        List<ArtType> artTypes
) {
}
