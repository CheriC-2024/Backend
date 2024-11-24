package com.art.cheric.module.artist.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "작가 작품 소장처 DTO")
public record ArtistArtStorageReqDto(
        @Schema(description = "소장처", example = "서울시립미술 소장관")
        @NotBlank(message = "소장처는 필수 값입니다.")
        @Size(min = 2, max = 30, message = "소장처는 2자이상 30자 이하여야 합니다.")
        String location
) {
}
