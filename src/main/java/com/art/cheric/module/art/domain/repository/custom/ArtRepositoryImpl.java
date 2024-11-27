package com.art.cheric.module.art.domain.repository.custom;

import com.art.cheric.global.enums.ArtOrderType;
import com.art.cheric.global.enums.ArtType;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.entity.QArt;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ArtRepositoryImpl implements ArtRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Art> getArtsBySortAndFilterAndPaging(Long userId, Boolean isCollectorsArt, ArtType artType,
                                                     ArtOrderType order, Pageable pageable) {
        QArt art = QArt.art;

        // 정렬 조건 설정
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifier(order, art);

        // 조건 설정
        BooleanExpression isCollectorsArtCondition = isCollectorsArt != null ? art.isCollectorsArt.eq(isCollectorsArt) : null;
        BooleanExpression artTypeCondition = artType != null ? art.artParts.any().artType.eq(artType) : null;
        BooleanExpression userCondition = userId != null ? art.user.id.eq(userId) : null;

        // 데이터 조회
        List<Art> results = jpaQueryFactory
                .selectFrom(art)
                .where(isCollectorsArtCondition, artTypeCondition, userCondition)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 데이터 개수 조회
        long total = jpaQueryFactory
                .select(art.count())
                .from(art)
                .where(isCollectorsArtCondition, artTypeCondition, userCondition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifier(ArtOrderType order, QArt art) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        switch (order) {
            case LATEST -> orderSpecifiers.add(art.createdAt.desc());
            case NAME -> orderSpecifiers.add(art.name.asc());
            case LIKE -> orderSpecifiers.add(art.heartCount.desc());
        }

        if (order != ArtOrderType.LATEST) {
            orderSpecifiers.add(art.createdAt.desc());
        }
        return orderSpecifiers;
    }


}
