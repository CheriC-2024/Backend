package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.Year;
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
public class Art extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String name;

    private String description;

    private String series;

    private String material;

    @NotNull
    private Year madeAt;

    @Column(nullable = true)
    private Integer cherryPrice;

    @NotNull
    private int horizontalSize;

    @NotNull
    private int verticalSize;

    @NotNull
    private String imgUrl;

    @NotNull
    private int heartCount;

    @NotNull
    private boolean isCollectorsArt;

    @OneToMany(mappedBy = "art", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ArtPart> artParts = new ArrayList<>();

    @OneToMany(mappedBy = "art", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ArtPlusImage> artPlusImages = new ArrayList<>();

    public static Art of(@NotNull User user, @NotNull String name, String description, String series, String material, @NotNull Year madeAt,
                         Integer cherryPrice, @NotNull int horizontalSize, @NotNull int verticalSize,
                         @NotNull String imgUrl, @NotNull boolean isCollectorsArt) {
        return Art.builder()
                .user(user)
                .name(name)
                .description(description)
                .series(series)
                .material(material)
                .madeAt(madeAt)
                .cherryPrice(cherryPrice)
                .horizontalSize(horizontalSize)
                .verticalSize(verticalSize)
                .heartCount(0)
                .imgUrl(imgUrl)
                .isCollectorsArt(isCollectorsArt)
                .build();
    }

    public void addArtPart(ArtPart artPart) {
        this.artParts.add(artPart);
    }

    public void removeArtPart(ArtPart artPart) {
        this.artParts.remove(artPart);
    }

    public void addArtPlusImages(ArtPlusImage imageUrl) {
        this.artPlusImages.add(imageUrl);
    }

    public void removeArtPlusImages(ArtPlusImage imageUrl) {
        this.artPlusImages.remove(imageUrl);
    }

    public void plusHeartCount() {
        this.heartCount += 1;
    }

    public void minusHeartCount() {
        this.heartCount -= 1;
    }
}
