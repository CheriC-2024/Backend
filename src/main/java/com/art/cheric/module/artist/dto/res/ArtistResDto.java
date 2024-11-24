package com.art.cheric.module.artist.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 상세 정보 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistResDto {
    @Schema(description = "작가 연락망")
    ArtistContactResDto artistContactRes;

    @Schema(description = "작가 학위 정보")
    List<ArtistDegreeResDto> artistDegreeRess;

    @Schema(description = "작가 전시 정보 (개인전/단체전)")
    List<ArtistExhibitionResDto> artistExhibitionRess;

    @Schema(description = "작가 소장처 정보")
    List<ArtistArtStorageResDto> artistArtStorageRess;

    @Schema(description = "작가 수상 정보")
    List<ArtistPrizeResDto> artistPrizeRess;

    @Schema(description = "작가 레지던시 정보")
    List<ArtistResidenceResDto> artistResidenceRess;

    public static ArtistResDto of(ArtistContactResDto artistContactRes,
                                  List<ArtistDegreeResDto> artistDegreeRess,
                                  List<ArtistExhibitionResDto> artistExhibitionRess,
                                  List<ArtistArtStorageResDto> artistArtStorageRess,
                                  List<ArtistPrizeResDto> artistPrizeRess,
                                  List<ArtistResidenceResDto> artistResidenceRess) {
        return ArtistResDto.builder()
                .artistContactRes(artistContactRes)
                .artistDegreeRess(artistDegreeRess)
                .artistExhibitionRess(artistExhibitionRess)
                .artistArtStorageRess(artistArtStorageRess)
                .artistPrizeRess(artistPrizeRess)
                .artistResidenceRess(artistResidenceRess)
                .build();
    }
}
