package com.art.cheric.module.artist.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ExhibitionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ArtistExhibition extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @NotNull
    private String name;

    @NotNull
    private String location;

    @NotNull
    private String byWho;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExhibitionType exhibitionType;

    @NotNull
    private Year openedAt;

    public static ArtistExhibition of(@NotNull Artist artist, @NotNull String name, @NotNull String location,
                                      @NotNull String byWho, @NotNull ExhibitionType exhibitionType,
                                      @NotNull Year openedAt) {
        return ArtistExhibition.builder()
                .artist(artist)
                .name(name)
                .location(location)
                .byWho(byWho)
                .exhibitionType(exhibitionType)
                .openedAt(openedAt)
                .build();
    }
}
