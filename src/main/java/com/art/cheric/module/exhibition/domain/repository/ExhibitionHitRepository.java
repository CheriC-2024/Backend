package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionHit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionHitRepository extends JpaRepository<ExhibitionHit, Long> {
    Optional<ExhibitionHit> findByUserIdAndExhibitionId(Long userId, Long exhibitionId);
}
