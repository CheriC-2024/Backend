package com.art.cheric.module.art.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.global.enums.ValidateState;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class OwnArt extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "art_id", nullable = false, unique = true)
    private Art art;

    @NotNull
    private String artistName;

    @NotNull
    private long price;

    @NotNull
    private boolean isPriceOpen;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ValidateState state;

    @OneToMany(mappedBy = "ownArt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ArtFile> artFiles = new ArrayList<>();

    public static OwnArt of(@NotNull Art art, @NotNull String artistName, @NotNull long price,
                            @NotNull boolean isPriceOpen) {
        return OwnArt.builder()
                .art(art)
                .artistName(artistName)
                .price(price)
                .isPriceOpen(isPriceOpen)
                .state(ValidateState.VALID_YET)
                .build();
    }

    public boolean isNotValidState() {
        return this.state != ValidateState.VALID;
    }

    public void addArtFile(ArtFile artFile) {
        this.artFiles.add(artFile);
    }

    public void removeArtFile(ArtFile artFile) {
        this.artFiles.remove(artFile);
    }

    public void updateValidateState(ValidateState state) {
        this.state = state;
    }
}
