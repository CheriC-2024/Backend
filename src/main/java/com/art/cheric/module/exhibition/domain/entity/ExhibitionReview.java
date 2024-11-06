package com.art.cheric.module.exhibition.domain.entity;

import com.art.cheric.global.common.BaseTime;
import com.art.cheric.module.user.domain.entity.User;
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
public class ExhibitionReview extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String message;

    @NotNull
    private int heartCount;

    @ManyToOne
    @JoinColumn(name = "exhibition_review_id")
    private ExhibitionReview exhibitionReview;

    public static ExhibitionReview of(@NotNull Exhibition exhibition, @NotNull User user, @NotNull String message,
                                      ExhibitionReview exhibitionReview) {
        return ExhibitionReview.builder()
                .exhibition(exhibition)
                .user(user)
                .message(message)
                .exhibitionReview(exhibitionReview)
                .heartCount(0)
                .build();
    }

    public void plusHeartCount() {
        this.heartCount += 1;
    }

    public void minusHeartCount() {
        this.heartCount -= 1;
    }

}
