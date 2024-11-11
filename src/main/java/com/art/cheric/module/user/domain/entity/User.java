package com.art.cheric.module.user.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String fcmToken;

    @NotNull
    private String deviceId;

    private String name;

    private String info;

    private String profileImgUrl;

    private String backgroundImgUrl;

    private boolean haveExperience;

    private boolean isArtist;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull
    private int cherryNum;

    @NotNull
    private int followerAmount;

    @NotNull
    private int followingAmount;

    public static User of(@NotNull String email, @NotNull String fcmToken, @NotNull String deviceId) {
        return User
                .builder()
                .email(email)
                .fcmToken(fcmToken)
                .deviceId(deviceId)
                .role(UserRole.COLLECTOR)
                .cherryNum(0)
                .followerAmount(0)
                .followingAmount(0)
                .build();
    }

    public void updateUserDetail(@NotNull String name, @NotNull String info, @NotNull String profileImgUrl,
                                 String backgroundImgUrl, @NotNull boolean haveExperience, @NotNull boolean isArtist) {
        this.name = name;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
        this.backgroundImgUrl = backgroundImgUrl;
        this.haveExperience = haveExperience;
        this.isArtist = isArtist;
    }

    public void plusCherryNum(int plusCherryAmount) {
        this.cherryNum += plusCherryAmount;
    }

    public void minusCherryNum(int minusCheeryAmount) {
        this.cherryNum -= minusCheeryAmount;
    }

    public void plusFollowing() {
        this.followingAmount += 1;
    }

    public void minusFollowing() {
        this.followingAmount -= 1;
    }

    public void plusFollower() {
        this.followerAmount += 1;
    }

    public void minusFollower() {
        this.followerAmount -= 1;
    }
}
