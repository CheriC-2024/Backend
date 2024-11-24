package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ValidateState;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.Embedded;
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
public class Artist extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ValidateState state;

    @Embedded
    ArtistBank artistBank; // 은행 정보를 위한 VO

    @Embedded
    ArtistContact artistContact; // 소셜 정보를 위한 VO

    public static Artist of(@NotNull User user, ArtistContact artistContact) {
        return Artist.builder()
                .user(user)
                .artistContact(artistContact)
                .state(ValidateState.VALID_YET)
                .build();
    }

    public void updateArtistBank(ArtistBank artistBank) {
        this.artistBank = artistBank;
    }

    public void updateArtistContact(ArtistContact artistContact) {
        this.artistContact = artistContact;
    }

    public void updateValidateState(ValidateState state) {
        this.state = state;
    }
}
