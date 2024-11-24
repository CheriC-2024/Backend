package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.ArtistDegree;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistDegreeRepository extends JpaRepository<ArtistDegree, String> {

    List<ArtistDegree> findAllByArtistId(Long artistId);
}
