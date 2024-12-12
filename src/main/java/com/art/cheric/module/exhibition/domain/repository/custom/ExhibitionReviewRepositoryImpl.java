package com.art.cheric.module.exhibition.domain.repository.custom;


import com.art.cheric.module.exhibition.domain.entity.ExhibitionReview;
import com.art.cheric.module.exhibition.domain.entity.QExhibitionReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ExhibitionReviewRepositoryImpl implements ExhibitionReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ExhibitionReview> findReviewsByExhibitionIdWithPaging(Long exhibitionId, Pageable pageable) {
        QExhibitionReview review = QExhibitionReview.exhibitionReview1;

        // 리뷰 조회
        List<ExhibitionReview> results = jpaQueryFactory
                .selectFrom(review)
                .where(review.exhibition.id.eq(exhibitionId))
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(review.exhibition.id.eq(exhibitionId))
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<ExhibitionReview> findReplyById(Long exhibitionReviewId) {
        QExhibitionReview exhibitionReview = QExhibitionReview.exhibitionReview1;

        // 주어진 exhibitionReviewId와 연결된 모든 대댓글을 가져오는 쿼리
        return jpaQueryFactory
                .selectFrom(exhibitionReview)
                .where(exhibitionReview.exhibitionReview.id.eq(exhibitionReviewId))
                .fetch();
    }
}
