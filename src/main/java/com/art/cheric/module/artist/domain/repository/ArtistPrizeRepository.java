package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.ArtistPrize;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistPrizeRepository extends JpaRepository<ArtistPrize, String> {
    List<ArtistPrize> findAllByArtistId(Long artistId);

}
