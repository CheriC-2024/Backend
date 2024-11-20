package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.ArtFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtFileRepository extends JpaRepository<ArtFile, Long> {
}
