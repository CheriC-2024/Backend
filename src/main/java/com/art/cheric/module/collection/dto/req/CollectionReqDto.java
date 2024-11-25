package com.art.cheric.module.collection.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "컬렉션 생성 요청 DTO")
public record CollectionReqDto(
        @Schema(description = "컬렉션 이름", example = "별빛 전시 후보 1")
        @NotBlank(message = "컬렉션 이름은 필수값입니다.")
        @Size(min = 2, max = 10, message = "컬렉션 이름은 2자이상 10자 이하여야 합니다.")
        String name,

        @Schema(description = "컬렉션 설명", example = "별빛 전시를 위한 후보군입니다. 다양한 색채에 조금 더 중점을 두어 준비했습니다.")
        @NotBlank(message = "컬렉션 설명은 필수값입니다.")
        @Size(min = 2, max = 300, message = "컬렉션 설명은 2자이상 300자 이하여야 합니다.")
        String description,

        @Schema(description = "작품 id", example = "1")
        @NotNull(message = "작품 id는 필수 값입니다.")
        Long artId
) {
}
