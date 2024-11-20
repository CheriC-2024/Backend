package com.art.cheric.module.art.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Schema(description = "소장 작품 저장 요청 DTO")
public record OwnArtReqDto(

        @Schema(description = "기본 작품 정보 DTO")
        @NotNull
        ArtReqDto artBasicInfo,

        @Schema(description = "저작권자", example = "이작가")
        @NotBlank
        @Size(max = 30, message = "작품의 저작권자 이름은 30자를 넘을 수 없습니다.")
        String artistName,

        @Schema(description = "작품 가격", example = "100000")
        @NotNull
        long price,

        @Schema(description = "작품 공개 여부", example = "true")
        @NotNull
        boolean isPriceOpen,

        @Schema(description = "소장 작품 인증 서류 url 리스트", example = "[\"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_VALIDATE_FILE/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ownArtFile1.pdf\",\"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_VALIDATE_FILE/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ownArtFile2.pdf\"]")
        @NotNull
        @Size(min = 1, max = 3, message = "작품 소장 인증 서류는 1개 이상, 3개 이하로 등록해야 합니다.")
        List<String> fileUrl,

        @Schema(description = "소장 작품 추가 사진 url 리스트", example = "[\"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ownArtImage2.png\",\"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ownArtImage3.png\"]")
        @Size(max = 2, message = "소장 작품 추가 사진은 2개까지만 첨부할 수 있습니다.")
        List<String> imgUrl
) {
}
