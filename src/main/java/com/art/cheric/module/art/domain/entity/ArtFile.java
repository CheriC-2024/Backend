package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ValidateState;
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
public class ArtFile extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "own_art_id", nullable = false)
    private OwnArt ownArt;

    @NotNull
    private String fileUrl;

    private ValidateState state;

    public static ArtFile of(@NotNull OwnArt ownArt, @NotNull String fileUrl) {
        return ArtFile.builder()
                .ownArt(ownArt)
                .fileUrl(fileUrl)
                .state(ValidateState.VALID_YET)
                .build();
    }

    public void updateValidateState(ValidateState state) {
        this.state = state;
    }

}
