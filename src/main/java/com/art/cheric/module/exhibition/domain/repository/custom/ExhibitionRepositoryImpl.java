package com.art.cheric.module.exhibition.domain.repository.custom;


import com.art.cheric.global.enums.ExhibitionOrderType;
import com.art.cheric.module.exhibition.domain.entity.Exhibition;
import com.art.cheric.module.exhibition.domain.entity.QExhibition;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
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
public class ExhibitionRepositoryImpl implements ExhibitionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Exhibition> getExhibitionsBySortAndFilterAndPaging(
            Long artId, Long userId, ExhibitionOrderType order, Pageable pageable) {
        QExhibition exhibition = QExhibition.exhibition;

        // 정렬 조건 설정
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifier(order, exhibition);

        // 조건 설정
        BooleanExpression artCondition = artId != null ? exhibition.exhibitionArts.any().art.id.eq(artId) : null;
        BooleanExpression userCondition = userId != null ? exhibition.user.id.eq(userId) : null;

        // 데이터 조회
        List<Exhibition> results = jpaQueryFactory
                .selectFrom(exhibition)
                .where(artCondition, userCondition)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 데이터 개수 조회
        long total = jpaQueryFactory
                .select(exhibition.count())
                .from(exhibition)
                .where(artCondition, userCondition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifier(ExhibitionOrderType order, QExhibition exhibition) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        switch (order) {
            case LATEST -> orderSpecifiers.add(exhibition.createdAt.desc());
            case NAME -> orderSpecifiers.add(exhibition.name.asc());
            case LIKE -> orderSpecifiers.add(exhibition.heartCount.desc());
            case HITS -> orderSpecifiers.add(exhibition.hits.desc());
        }

        if (order != ExhibitionOrderType.LATEST) {
            orderSpecifiers.add(exhibition.createdAt.desc());
        }
        return orderSpecifiers;
    }
}
