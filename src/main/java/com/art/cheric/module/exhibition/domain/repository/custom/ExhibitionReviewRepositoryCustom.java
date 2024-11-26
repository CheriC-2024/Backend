package com.art.cheric.module.exhibition.domain.repository.custom;

import com.art.cheric.module.exhibition.domain.entity.ExhibitionReview;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionReviewRepositoryCustom {
    Page<ExhibitionReview> findReviewsByExhibitionIdWithPaging(Long exhibitionId, Pageable pageable);
    List<ExhibitionReview> findReplyById(Long exhibitionReviewId);

}
