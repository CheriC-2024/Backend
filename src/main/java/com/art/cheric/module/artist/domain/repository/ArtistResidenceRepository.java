package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.ArtistResidence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistResidenceRepository extends JpaRepository<ArtistResidence, Long> {
    List<ArtistResidence> findAllByArtistId(Long artistId);

}
