package com.art.cheric.module.exhibition.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ExhibitionBackgroundType;
import com.art.cheric.global.enums.FontColorType;
import com.art.cheric.global.enums.FontType;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
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
public class Exhibition extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FontType font;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FontColorType fontColor;

    private String coverImgUrl;

    private String colors;

    @Enumerated(EnumType.STRING)
    private ExhibitionBackgroundType exhibitionBackgroundType;

    private String musicUrl;

    @NotNull
    private int heartCount;

    @NotNull
    private int hits;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ExhibitionArt> exhibitionArts = new ArrayList<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ExhibitionTheme> exhibitionThemes = new ArrayList<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<ExhibitionBackgroundColor> exhibitionBackgroundColors = new ArrayList<>();

    public static Exhibition of(@NotNull User user, @NotNull String name, @NotNull String description,
                                @NotNull FontType font, @NotNull FontColorType fontColor, String coverImgUrl,
                                ExhibitionBackgroundType exhibitionBackgroundType, @NotNull String musicUrl) {
        return Exhibition.builder()
                .user(user)
                .name(name)
                .description(description)
                .font(font)
                .fontColor(fontColor)
                .coverImgUrl(coverImgUrl)
                .exhibitionBackgroundType(exhibitionBackgroundType)
                .musicUrl(musicUrl)
                .heartCount(0)
                .hits(0)
                .build();
    }

    public void plusHeartCount() {
        this.heartCount += 1;
    }

    public void minusHeartCount() {
        this.heartCount -= 1;
    }

    public void plusHits() {
        this.hits += 1;
    }

}
