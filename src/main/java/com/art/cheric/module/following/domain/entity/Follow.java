package com.art.cheric.module.following.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Follow extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private User followingUser;

    @ManyToOne
    @JoinColumn(name = "followed_user_id", nullable = false)
    private User followedUser;

    public static Follow of(@NotNull User followingUser, @NotNull User followedUser) {
        return Follow.builder()
                .followingUser(followingUser)
                .followedUser(followedUser)
                .build();
    }
}
