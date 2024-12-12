package com.art.cheric.module.user.dto.res;

import com.art.cheric.global.enums.ArtType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "hot한 사용자 추천 리스트 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class HotUserListResDto {

    @Schema(description = "사용자 id", example = "1")
    private final Long id;

    @Schema(description = "사용자 이름", example = "이예림")
    private final String name;

    @Schema(description = "사용자 프로필 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
    private final String profileImgUrl;

    @Schema(description = "사용자 선호 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
    private final List<ArtType> artTypes;

    @Schema(description = "사용자 소개", example = "저는 3년차 컬렉터로, 회화 분야를 주로 수집하고 있습니다.")
    private final String description;

    @Schema(description = "작품 이미지 경로", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png")
    private final String artImgUrl;

    public static HotUserListResDto of(Long id, String name, String profileImgUrl, List<ArtType> artTypes,
                                       String description, String artImgUrl) {
        return HotUserListResDto.builder()
                .id(id)
                .name(name)
                .profileImgUrl(profileImgUrl)
                .artTypes(artTypes)
                .description(description)
                .artImgUrl(artImgUrl)
                .build();
    }
}
