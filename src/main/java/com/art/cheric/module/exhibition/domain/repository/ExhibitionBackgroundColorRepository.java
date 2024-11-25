package com.art.cheric.module.exhibition.domain.repository;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionBackgroundColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionBackgroundColorRepository extends JpaRepository<ExhibitionBackgroundColor, Long> {

}
