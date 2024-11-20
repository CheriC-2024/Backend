package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.ArtPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtPartRepository extends JpaRepository<ArtPart, Long> {
}
