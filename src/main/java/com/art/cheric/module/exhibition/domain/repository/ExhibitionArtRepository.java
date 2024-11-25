package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionArt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionArtRepository extends JpaRepository<ExhibitionArt, Long> {
    @Query("SELECT ea FROM ExhibitionArt ea WHERE ea.exhibition.id = :exhibitionId ORDER BY ea.num ASC")
    List<ExhibitionArt> findByExhibitionIdOrderByNum(@Param("exhibitionId") Long exhibitionId);

}
