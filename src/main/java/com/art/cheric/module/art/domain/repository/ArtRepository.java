package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.repository.custom.ArtRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtRepository extends JpaRepository<Art, Long>, ArtRepositoryCustom {
}
