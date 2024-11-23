package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.common.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.time.Year;
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
public class ArtistPrize extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @NotNull
    private String organization;

    @NotNull
    private String level;

    @NotNull
    private Year receivedAt;

    public static ArtistPrize of(@NotNull Artist artist, @NotNull String organization, @NotNull String level,
                                 @NotNull Year receivedAt) {
        return ArtistPrize.builder()
                .artist(artist)
                .organization(organization)
                .level(level)
                .receivedAt(receivedAt)
                .build();
    }
}
