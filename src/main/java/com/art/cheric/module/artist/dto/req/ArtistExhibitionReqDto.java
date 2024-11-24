package com.art.cheric.module.artist.dto.req;


import com.art.cheric.global.enums.ExhibitionType;
import com.art.cheric.global.validation.annotation.ValidYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Year;

@Schema(description = "작가 전시 DTO")
public record ArtistExhibitionReqDto(
        @Schema(description = "전시 이름", example = "별빛:하늘을 그리다")
        @NotBlank(message = "전시 이름은 필수 값입니다.")
        @Size(min = 2, max = 30, message = "전시 이름은 2자이상 30자 이하여야 합니다.")
        String exhibitionName,
        @Schema(description = "개최 장소", example = "서울시립미술관")
        @NotNull(message = "개최 장소는 필수 정보입니다.")
        @Size(min = 2, max = 50, message = "개최 장소는 2자이상 50자 이하여야 합니다.")
        String location,

        @Schema(description = "주최 단체/개인명", example = "김진솔")
        @NotNull(message = "주최 단체/개인명은 필수 값입니다.")
        @Size(min = 2, max = 30, message = "주최 단체/개인명은 2자이상 30자 이하여야 합니다.")
        String byWho,

        @Schema(description = "개인전/단체전 정보", example = "INDIVIDUAL")
        @NotNull(message = "개인전/단체전 정보는 필수 값입니다.")
        ExhibitionType exhibitionType,

        @Schema(description = "개최 년도", example = "2024")
        @ValidYear
        @NotNull(message = "개최 년도는 필수 값입니다.")
        Year openedAt

) {
}
