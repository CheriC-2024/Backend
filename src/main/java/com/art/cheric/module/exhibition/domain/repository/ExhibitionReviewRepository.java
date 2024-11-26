package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionReview;
import com.art.cheric.module.exhibition.domain.repository.custom.ExhibitionReviewRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long>,
        ExhibitionReviewRepositoryCustom {
    @Query(value = "SELECT * FROM exhibition_review " +
            "WHERE exhibition_review_id IS NULL " +
            "AND exhibition_id = :exhibitionId " +
            "ORDER BY heart_count DESC, created_at DESC LIMIT 1", nativeQuery = true)
    ExhibitionReview findTopReviewByExhibitionId(@Param("exhibitionId") Long exhibitionId);

    Optional<ExhibitionReview> findByIdAndExhibitionIdAndUserId(Long id, Long exhibitionId, Long userId);

    Integer countByExhibitionIdAndExhibitionReviewId(Long exhibitionId, Long exhibitionReviewId);

    Optional<ExhibitionReview> findByIdAndExhibitionId(Long id, Long exhibitionId);

}
