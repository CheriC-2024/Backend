package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.ArtHeart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtHeartRepository extends JpaRepository<ArtHeart, Long> {
    Optional<ArtHeart> findByArtIdAndUserId(Long artId, Long userId);
}
