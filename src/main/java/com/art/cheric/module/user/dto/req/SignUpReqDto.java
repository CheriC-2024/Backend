package com.art.cheric.module.user.dto.req;


import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.validation.annotation.UniqueElements;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "사용자 가입 요청 DTO")
public record SignUpReqDto(
        @Schema(description = "사용자 이름", example = "cheric-collector")
        @NotBlank(message = "사용자 이름은 필수 값입니다.")
        @Size(min = 2, max = 10, message = "사용자 이름은 2자이상 10자 미만이어야 합니다.")
        String name,

        @Schema(description = "사용자 소개", example = "저는 컬렉팅을 즐기는 사람입니다.")
        @NotBlank(message = "사용자 소개는 필수 값입니다.")
        @Size(max = 100, message = "사용자 소개는 100자 이하여야 합니다.")
        String info,

        @Schema(description = "사용자 프로필 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
        @NotBlank(message = "사용자 프로필 이미지는 필수 값입니다.")
        String profileImgUrl,

        @Schema(description = "사용자 배경 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
        String backgroundImgUrl,

        @Schema(description = "사용자 컬렉팅 경험 여부", example = "false")
        @NotNull(message = "사용자 컬렉팅 경험 여부는 필수 값입니다.")
        boolean haveExperience,

        @Schema(description = "사용자 작가 여부", example = "false")
        @NotNull(message = "사용자 작가 여부는 필수 값입니다.")
        boolean isArtist,

        @Schema(description = "사용자 선호 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
        @NotEmpty(message = "사용자 선호 분야는 필수 값입니다.")
        @UniqueElements
        @Size(max = 2, message = "사용자 선호 분야는 2개까지 입력 가능합니다.")
        List<ArtType> userPartRequests
) {
}
