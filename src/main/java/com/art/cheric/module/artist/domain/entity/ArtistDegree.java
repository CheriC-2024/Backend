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
public class ArtistDegree extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @NotNull
    private String name;

    @NotNull
    private String major;

    @NotNull
    private Year entranceAt;

    @NotNull
    private Year graduateAt;

    public static ArtistDegree of(@NotNull Artist artist, @NotNull String name, @NotNull String major,
                                  @NotNull Year entranceAt, @NotNull Year graduateAt) {
        return ArtistDegree.builder()
                .artist(artist)
                .name(name)
                .major(major)
                .entranceAt(entranceAt)
                .graduateAt(graduateAt)
                .build();
    }
}
