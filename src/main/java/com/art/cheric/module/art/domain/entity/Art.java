package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Art extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    private String series;

    private String material;

    @NotNull
    private Year madeAt;

    @NotNull
    private int cherryPrice;

    @NotNull
    private int horizontalSize;

    @NotNull
    private int verticalSize;

    @NotNull
    private String imgUrl;

    @NotNull
    private int heartCount;

    public static Art of(@NotNull String name, String description, String series, String material, @NotNull Year madeAt,
                         @NotNull int cherryPrice, @NotNull int horizontalSize, @NotNull int verticalSize,
                         @NotNull String imgUrl) {
        return Art.builder()
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
                .build();
    }

    public void plusHeartCount() {
        this.heartCount += 1;
    }

    public void minusHeartCount() {
        this.heartCount -= 1;
    }
}
