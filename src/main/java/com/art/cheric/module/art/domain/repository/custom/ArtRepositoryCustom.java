package com.art.cheric.module.art.domain.repository.custom;

import com.art.cheric.global.enums.ArtOrderType;
import com.art.cheric.global.enums.ArtType;
import com.art.cheric.module.art.domain.entity.Art;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtRepositoryCustom {
    Page<Art> getArtsBySortAndFilterAndPaging(
            Long userId, Boolean isCollectorsArt, ArtType artType, ArtOrderType order, Pageable pageable);

}
