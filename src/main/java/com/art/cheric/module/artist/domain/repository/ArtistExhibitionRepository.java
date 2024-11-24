package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.ArtistExhibition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistExhibitionRepository extends JpaRepository<ArtistExhibition, String> {

    List<ArtistExhibition> findAllByArtistId(Long artistId);
}
