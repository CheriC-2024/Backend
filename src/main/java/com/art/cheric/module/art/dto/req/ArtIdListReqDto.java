package com.art.cheric.module.art.dto.req;


import com.art.cheric.global.validation.annotation.UniqueElements;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "작품 리스트 요청 DTO")
public record ArtIdListReqDto(
        @Schema(description = "요청하는 작품 id 리시트", example = "[1, 2, 3]")
        @NotEmpty
        @UniqueElements
        List<Long> artIds
) {
}
