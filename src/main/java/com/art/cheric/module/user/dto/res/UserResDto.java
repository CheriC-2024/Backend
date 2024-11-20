package com.art.cheric.module.user.dto.res;


import com.art.cheric.global.enums.ArtType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "사용자 간단 정보 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserResDto {
    @Schema(description = "사용자 이름", example = "이예림")
    private final String name;

    @Schema(description = "사용자 소개", example = "저는 3년차 컬렉터로, 회화 분야를 주로 수집하고 있습니다.")
    private final String description;

    @Schema(description = "사용자 선호 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
    private final List<ArtType> artTypes;

    public static UserResDto of(String name, String description, List<ArtType> artTypes) {
        return UserResDto.builder()
                .name(name)
                .description(description)
                .artTypes(artTypes)
                .build();
    }
}
