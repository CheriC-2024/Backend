package com.art.cheric.global.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "팔로우, 팔로잉 분류 타입")
@Getter
public enum FollowSortType {
    @Schema(description = "follower")
    FOLLOWER("팔로워"),
    @Schema(description = "following")
    FOLLOWING("팔로잉");


    private final String value;

    FollowSortType(String value) {
        this.value = value;
    }
}
