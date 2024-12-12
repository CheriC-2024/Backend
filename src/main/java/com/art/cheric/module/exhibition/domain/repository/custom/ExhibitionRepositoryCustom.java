package com.art.cheric.module.exhibition.domain.repository.custom;

import com.art.cheric.global.enums.ExhibitionOrderType;
import com.art.cheric.module.exhibition.domain.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionRepositoryCustom {
    Page<Exhibition> getExhibitionsBySortAndFilterAndPaging(Long artId, Long userId, ExhibitionOrderType order,
                                                            Pageable pageable);
}
