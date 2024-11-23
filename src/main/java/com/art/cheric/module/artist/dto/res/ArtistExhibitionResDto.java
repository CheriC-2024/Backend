package com.art.cheric.module.artist.dto.res;


import com.art.cheric.global.enums.ExhibitionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Year;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 전시 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistExhibitionResDto {
    @Schema(description = "전시 이름", example = "별빛:하늘을 그리다")
    private final String exhibitionName;

    @Schema(description = "개최 장소", example = "서울시립미술관")
    private final String location;

    @Schema(description = "주최 단체/개인명", example = "김진솔")
    private final String byWho;

    @Schema(description = "개인전/단체전 정보", example = "INDIVIDUAL")
    private final ExhibitionType exhibitionType;

    @Schema(description = "개최 년도", example = "2024")
    private final Year openedAt;

    public static ArtistExhibitionResDto of(String exhibitionName, String location, String byWho,
                                            ExhibitionType exhibitionType, Year openedAt) {
        return ArtistExhibitionResDto.builder()
                .exhibitionName(exhibitionName)
                .location(location)
                .byWho(byWho)
                .exhibitionType(exhibitionType)
                .openedAt(openedAt)
                .build();
    }
}
