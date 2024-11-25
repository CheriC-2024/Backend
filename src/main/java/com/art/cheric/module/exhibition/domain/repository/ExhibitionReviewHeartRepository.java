package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionReviewHeart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionReviewHeartRepository extends JpaRepository<ExhibitionReviewHeart, Long> {
    Optional<ExhibitionReviewHeart> findByExhibitionReviewIdAndUserId(Long reviewId, Long userId);
}
