package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
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
public class ArtPlusImage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @NotNull
    private String imgUrl;

    public static ArtPlusImage of(@NotNull Art art, @NotNull String imgUrl) {
        return ArtPlusImage.builder()
                .art(art)
                .imgUrl(imgUrl)
                .build();
    }
}
