package com.art.cheric.module.user.dto.res;

import com.art.cheric.module.art.dto.res.ArtBriefResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 추천 리스트 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserListResDto {

    @Schema(description = "사용자 id", example = "1")
    private final Long id;

    @Schema(description = "사용자 이름", example = "이예림")
    private final String name;

    @Schema(description = "사용자 프로필 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
    private final String profileImgUrl;

    @Schema(description = "작품 리스트 응답 dto")
    private final List<ArtBriefResDto> artBriefRess;

    public static UserListResDto of(Long id, String name, String profileImgUrl, List<ArtBriefResDto> artBriefRess) {
        return UserListResDto.builder()
                .id(id)
                .name(name)
                .profileImgUrl(profileImgUrl)
                .artBriefRess(artBriefRess)
                .build();
    }
}
