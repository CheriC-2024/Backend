package com.art.cheric.module.user.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.UserRole;
import com.art.cheric.module.following.domain.entity.Follow;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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

    private boolean isArtist; // 작가 등록 여부와 관계 없이 회원가입 시 받는 데이터

    @NotNull
    private boolean isValidateArtist; // 실제 서비스 내에서 작가 등록 및 인증에 따라 변경되는 데이터

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull
    private int myCherryNum;

    private Integer soldCherryNum;

    @NotNull
    private int followerAmount;

    @NotNull
    private int followingAmount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<UserPart> userParts = new ArrayList<>();

    @OneToMany(mappedBy = "followingUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "followedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Follow> followers = new ArrayList<>();

    public static User of(@NotNull String email, @NotNull String fcmToken, @NotNull String deviceId) {
        return User
                .builder()
                .email(email)
                .fcmToken(fcmToken)
                .deviceId(deviceId)
                .isValidateArtist(false)
                .role(UserRole.COLLECTOR)
                .myCherryNum(0)
                .soldCherryNum(null)
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

    public void updateUserDetailAsArtist(@NotNull String name, @NotNull String info, @NotNull String profileImgUrl,
                                 @NotNull List<UserPart> userArtType) {
        this.name = name;
        this.info = info;
        this.profileImgUrl = profileImgUrl;
        this.userParts = userArtType;
    }

    public void updateIsValidateArtist(){
        this.isValidateArtist = true;
    }

    public void addUserPart(UserPart userPart) {
        this.userParts.add(userPart);
    }

    public void removeUserPart(UserPart userPart) {
        this.userParts.remove(userPart);
    }

    public void plusMyCherryNum(int plusMyCherryAmount) {
        this.myCherryNum += plusMyCherryAmount;
    }

    public void minusMyCherryNum(int minusMyCheeryAmount) {
        this.myCherryNum -= minusMyCheeryAmount;
    }

    public void plusSoldCherryNum(int plusSoldCherryAmount) {
        this.soldCherryNum += plusSoldCherryAmount;
    }

    public void minusSoldCherryNum(int minusSoldCheeryAmount) {
        this.soldCherryNum -= minusSoldCheeryAmount;
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
