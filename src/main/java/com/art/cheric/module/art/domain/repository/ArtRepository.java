package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.repository.custom.ArtRepositoryCustom;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ArtRepository extends JpaRepository<Art, Long>, ArtRepositoryCustom {
    Optional<Art> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT a FROM Art a WHERE a.user.id = :userId ORDER BY a.createdAt DESC")
    List<Art> findMostRecentArtByUserId(@Param("userId") Long userId, Pageable pageable);

}
