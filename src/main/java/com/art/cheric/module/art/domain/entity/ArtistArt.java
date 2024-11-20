package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class ArtistArt extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    private boolean isUsable;

    public static ArtistArt of(@NotNull Art art, @NotNull User user) {
        return ArtistArt.builder()
                .art(art)
                .user(user)
                .isUsable(true)
                .build();
    }
}
