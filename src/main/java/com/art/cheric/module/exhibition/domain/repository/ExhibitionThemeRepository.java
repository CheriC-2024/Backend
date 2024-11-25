package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionThemeRepository extends JpaRepository<ExhibitionTheme, Long> {
}
