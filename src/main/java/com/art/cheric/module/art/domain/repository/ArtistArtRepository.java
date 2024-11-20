package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.ArtistArt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistArtRepository extends JpaRepository<ArtistArt, Long> {
    Optional<ArtistArt> findByUserIdAndArtId(Long userId, Long artId);

}
