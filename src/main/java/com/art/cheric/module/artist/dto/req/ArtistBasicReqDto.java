package com.art.cheric.module.artist.dto.req;


import com.art.cheric.global.enums.ArtType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "작가 기본 정보 DTO")
public record ArtistBasicReqDto(
        @Schema(description = "작가 이름", example = "artist")
        @NotBlank(message = "작가 이름은 필수 값입니다.")
        @Size(min = 2, max = 10, message = "작가 이름은 2자이상 10자 이하여야 합니다.")
        String name,

        @Schema(description = "작가 소개", example = "저는 3년차 회화 분야 작가입니다.")
        @NotBlank(message = "작가 소개는 필수 값입니다.")
        @Size(max = 100, message = "작가 소개는 100자 이하여야 합니다.")
        String info,

        @Schema(description = "작가 프로필 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
        @NotBlank(message = "작가 프로필 이미지는 필수 값입니다.")
        String profileImgUrl,

        @Schema(description = "작가 선호 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
        @NotEmpty(message = "작가 선호 분야는 필수 값입니다.")
        @Size(max = 2, message = "작가 선호 분야는 2개까지 입력 가능합니다.")
        List<ArtType> userPartRequests
) {
}
