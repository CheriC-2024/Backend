package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.ArtistArtStorage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistArtStorageRepository extends JpaRepository<ArtistArtStorage, String> {
    List<ArtistArtStorage> findAllByArtistId(Long artistId);

}
