package com.art.cheric.module.exhibition.dto.req;

import com.art.cheric.global.enums.CloudVisionType;
import com.art.cheric.global.validation.annotation.UniqueElements;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "Cloud Vision 요청 DTO")
public record ArtCloudReqDto(
        @Schema(description = "요청하는 Cloud Vision 종류", example = "LABEL_DETECTION")
        @NotNull
        @Enumerated(EnumType.STRING)
        CloudVisionType cloudVisionType,

        @Schema(description = "요청하는 작품 id", example = "[1, 2, 3]")
        @NotEmpty
        @UniqueElements
        @Size(max = 30, message = "작품은 최대 30개까지만 설정할 수 있습니다.")
        List<Long> artIds
) {
}
