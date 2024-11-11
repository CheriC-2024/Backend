package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ValidateState;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class OwnArt extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String artistName;

    @NotNull
    private long price;

    @NotNull
    private boolean isPriceOpen;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ValidateState state;

    public static OwnArt of(@NotNull Art art, @NotNull User user, @NotNull String artistName, @NotNull long price,
                            @NotNull boolean isPriceOpen) {
        return OwnArt.builder()
                .art(art)
                .user(user)
                .artistName(artistName)
                .price(price)
                .isPriceOpen(isPriceOpen)
                .state(ValidateState.VALID_YET)
                .build();
    }

    public void updateValidateState(ValidateState state) {
        this.state = state;
    }
}
