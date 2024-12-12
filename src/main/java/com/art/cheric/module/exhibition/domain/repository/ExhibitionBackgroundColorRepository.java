package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionBackgroundColor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionBackgroundColorRepository extends JpaRepository<ExhibitionBackgroundColor, Long> {
    @Query("SELECT ebc.colors FROM ExhibitionBackgroundColor ebc WHERE ebc.exhibition.id = :exhibitionId ORDER BY ebc.num ASC")
    List<String> findByExhibitionIdOrderByNum(@Param("exhibitionId") Long exhibitionId);

}
