package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ValidateState;
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
public class ArtistFile extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @NotNull
    private String fileUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ValidateState state;

    public static ArtistFile of(@NotNull Artist artist, @NotNull String fileUrl) {
        return ArtistFile.builder()
                .artist(artist)
                .fileUrl(fileUrl)
                .state(ValidateState.VALID_YET)
                .build();
    }

    public void updateValidateState(ValidateState state) {
        this.state = state;
    }
}
