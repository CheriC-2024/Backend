package com.art.cheric.module.art.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "소장 작품 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class OwnArtResDto {

    @Schema(description = "소장 작품 매입 가격", example = "작품 가격 공개 여부에서 공개를 했을 시, 보여지는 정보")
    private final long price;

    public static OwnArtResDto from(long price) {
        return OwnArtResDto.builder()
                .price(price)
                .build();
    }
}
