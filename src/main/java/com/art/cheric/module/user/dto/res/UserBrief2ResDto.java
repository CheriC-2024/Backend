package com.art.cheric.module.user.dto.res;

import com.art.cheric.global.enums.ArtType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 간단 정보 ver2 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserBrief2ResDto {

    @Schema(description = "사용자 id", example = "1")
    private final Long id;

    @Schema(description = "사용자 이름", example = "이예림")
    private final String name;

    @Schema(description = "사용자 프로필 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
    private final String profileImgUrl;

    @Schema(description = "사용자 선호 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
    private final List<ArtType> artTypes;

    @Schema(description = "팔로잉 여부", example = "true")
    private final boolean isFollowing;

    public static UserBrief2ResDto of(Long id, String name, String profileImgUrl, List<ArtType> artTypes, boolean isFollowing) {
        return UserBrief2ResDto.builder()
                .id(id)
                .name(name)
                .profileImgUrl(profileImgUrl)
                .artTypes(artTypes)
                .isFollowing(isFollowing)
                .build();
    }
}
