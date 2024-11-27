package com.art.cheric.module.user.dto.res;


import com.art.cheric.global.enums.ArtType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 상세 정보 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserDetailResDto {

    @Schema(description = "사용자 id", example = "1")
    private final Long id;

    @Schema(description = "사용자 작가 여부 데이터", example = "false")
    private final boolean isValidateArtist;

    @Schema(description = "사용자 이름", example = "이예림")
    private final String name;

    @Schema(description = "사용자 소개", example = "저는 3년차 컬렉터로, 회화 분야를 주로 수집하고 있습니다.")
    private final String description;

    @Schema(description = "사용자 선호 분야", example = "[\"PAINTING\", \"OIL_PAINTING\"]")
    private final List<ArtType> artTypes;

    @Schema(description = "사용자 프로필 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
    private final String profileImgUrl;

    @Schema(description = "사용자 배경 이미지", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png")
    private final String backgroundImgUrl;

    @Schema(description = "사용자 팔로워 수", example = "100")
    private final int followerAmount;

    @Schema(description = "사용자 팔로잉 수", example = "123")
    private final int followingAmount;

    @Schema(description = "사용자 보유 체리 수", example = "45")
    private final int myCherryNum;

    @Schema(description = "사용자 정산 체리 수", example = "23")
    private final Integer soldCherryNum;

    @Schema(description = "팔로잉 여부", example = "true")
    private final boolean isFollowing;

    public static UserDetailResDto of(Long id, boolean isValidateArtist, String name, String description,
                                      List<ArtType> artTypes, String profileImgUrl, String backgroundImgUrl,
                                      int followerAmount, int followingAmount, int myCherryNum,
                                      Integer soldCherryNum, boolean isFollowing) {
        return UserDetailResDto.builder()
                .id(id)
                .isValidateArtist(isValidateArtist)
                .name(name)
                .description(description)
                .artTypes(artTypes)
                .profileImgUrl(profileImgUrl)
                .backgroundImgUrl(backgroundImgUrl)
                .followerAmount(followerAmount)
                .followingAmount(followingAmount)
                .myCherryNum(myCherryNum)
                .soldCherryNum(soldCherryNum)
                .isFollowing(isFollowing)
                .build();
    }
}
