package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionHeart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionHeartRepository extends JpaRepository<ExhibitionHeart, Long> {
    Optional<ExhibitionHeart> findByUserIdAndExhibitionId(Long userId, Long exhibitionId);
}
