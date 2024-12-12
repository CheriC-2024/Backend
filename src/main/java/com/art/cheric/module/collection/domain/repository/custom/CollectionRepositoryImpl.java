package com.art.cheric.module.collection.domain.repository.custom;

import com.art.cheric.global.enums.BasicOrderType;
import com.art.cheric.module.art.domain.entity.QArt;
import com.art.cheric.module.art.dto.res.ArtBriefResDto;
import com.art.cheric.module.collection.domain.entity.QCollection;
import com.art.cheric.module.collection.domain.entity.QCollectionArt;
import com.art.cheric.module.collection.dto.res.CollectionArtResDto;
import com.art.cheric.module.collection.dto.res.CollectionResDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;import java.util.LinkedHashMap;



@Repository
@RequiredArgsConstructor
@Slf4j
public class CollectionRepositoryImpl implements CollectionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CollectionResDto> getCollection(long userId) {
        QCollection collection = QCollection.collection;
        QCollectionArt collectionArt = QCollectionArt.collectionArt;
        QArt art = QArt.art;

        // 서브쿼리로 각 컬렉션의 최신 작품 id를 가져오기
        JPQLQuery<Long> latestArtSubQuery = JPAExpressions
                .select(collectionArt.id.max())
                .from(collectionArt)
                .where(collectionArt.collection.id.eq(collection.id));

        // 메인 쿼리
        List<Tuple> results = jpaQueryFactory
                .select(
                        collection.id,
                        collection.name,
                        collection.description,
                        art.imgUrl
                )
                .from(collection)
                .leftJoin(collectionArt).on(collection.id.eq(collectionArt.collection.id))
                .leftJoin(collectionArt.art, art)
                .where(collection.user.id.eq(userId)
                        .and(collectionArt.id.eq(latestArtSubQuery)))
                .orderBy(collection.createdAt.desc())
                .fetch();

        // 결과 매핑
        return results.stream()
                .map(tuple -> CollectionResDto.of(
                        tuple.get(collection.id),
                        tuple.get(art.imgUrl),
                        tuple.get(collection.name),
                        tuple.get(collection.description)
                ))
                .toList();
    }

    @Override
    public List<CollectionArtResDto> getCollectionByCollectionIds(long userId, List<Long> collectionIds,
                                                                  BasicOrderType order) {
        QCollection collection = QCollection.collection;
        QCollectionArt collectionArt = QCollectionArt.collectionArt;
        QArt art = QArt.art;

        // 정렬 기준 설정
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        orderSpecifiers.add(collection.createdAt.desc()); // 공통 정렬 조건

        // 동적 정렬 조건 추가
        if (order != null) {
            switch (order) {
                case LATEST -> orderSpecifiers.add(collectionArt.createdAt.desc());
                case NAME -> orderSpecifiers.add(art.name.asc());
            }
        }

        // 쿼리 실행
        List<Tuple> results = jpaQueryFactory
                .select(
                        collection.id,
                        collection.name,
                        collection.description,
                        art.id,
                        art.imgUrl,
                        art.name,
                        art.isCollectorsArt,
                        art.cherryPrice
                )
                .from(collection)
                .leftJoin(collectionArt).on(collection.id.eq(collectionArt.collection.id))
                .leftJoin(collectionArt.art, art)
                .where(collection.user.id.eq(userId)
                        .and(collection.id.in(collectionIds)))
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .fetch();

        // 그룹화된 결과의 순서 유지
        Map<Long, List<ArtBriefResDto>> groupedResults = results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(collection.id), // 컬렉션 ID 기준으로 그룹화
                        LinkedHashMap::new, // 순서 유지
                        Collectors.mapping(tuple -> ArtBriefResDto.of(
                                tuple.get(art.id),
                                tuple.get(art.isCollectorsArt),
                                tuple.get(art.imgUrl),
                                tuple.get(art.cherryPrice),
                                tuple.get(art.name)
                        ), Collectors.toList())
                ));

        // 결과 매핑
        return groupedResults.entrySet().stream()
                .map(entry -> {
                    Long collectionId = entry.getKey();
                    List<ArtBriefResDto> artBriefResDtos = entry.getValue();
                    Tuple firstCollectionTuple = results.stream()
                            .filter(tuple -> Objects.equals(tuple.get(collection.id), collectionId))
                            .findFirst()
                            .orElseThrow();

                    return CollectionArtResDto.of(
                            collectionId,
                            firstCollectionTuple.get(collection.name),
                            firstCollectionTuple.get(collection.description),
                            artBriefResDtos
                    );
                })
                .toList();
    }
}
