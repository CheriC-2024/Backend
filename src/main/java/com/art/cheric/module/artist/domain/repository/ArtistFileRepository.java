package com.art.cheric.module.artist.domain.repository;

import com.art.cheric.module.artist.domain.entity.ArtistFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistFileRepository extends JpaRepository<ArtistFile, String> {

}
