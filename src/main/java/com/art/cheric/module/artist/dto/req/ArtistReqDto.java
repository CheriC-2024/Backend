package com.art.cheric.module.artist.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "작가 인증 요청 DTO")
public record ArtistReqDto(

        @Schema(description = "작가 기본 정보")
        @NotNull
        ArtistBasicReqDto artistBasicReq,

        @Schema(description = "작가 인증 서류 url 리스트", example = "[\"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_VALIDATE_FILE/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ownArtFile1.pdf\",\"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_VALIDATE_FILE/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ownArtFile2.pdf\"]")
        @NotNull
        @Size(min = 1, max = 3, message = "작가 인증 서류는 1개 이상, 3개 이하로 등록해야 합니다.")
        List<String> fileUrl,

        @Schema(description = "작가 연락망")
        ArtistContactReqDto artistContactReq,

        @Schema(description = "작가 학위 정보")
        List<ArtistDegreeReqDto> artistDegreeReqs,

        @Schema(description = "작가 전시 정보 (개인전/단체전)")
        List<ArtistExhibitionReqDto> artistExhibitionReqs,

        @Schema(description = "작가 소장처 정보")
        List<ArtistArtStorageReqDto> artistArtStorageReqs,

        @Schema(description = "작가 수상 정보")
        List<ArtistPrizeReqDto> artistPrizeReqs,

        @Schema(description = "작가 레지던시 정보")
        List<ArtistResidenceReqDto> artistResidenceReqs
) {
}
