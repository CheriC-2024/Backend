package com.art.cheric.module.exhibition.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.art.domain.entity.Art;
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
public class ExhibitionArt extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    @ManyToOne
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @NotNull
    private String description;

    private String reasonForPurchase;

    private String review;

    private int num;

    public static ExhibitionArt of(@NotNull Exhibition exhibition, @NotNull Art art, @NotNull String description,
                                   String reasonForPurchase, String review,  @NotNull int num) {
        return ExhibitionArt.builder()
                .exhibition(exhibition)
                .art(art)
                .description(description)
                .reasonForPurchase(reasonForPurchase)
                .review(review)
                .num(num)
                .build();
    }

}
