package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.Artist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUserId(Long userId);

}
