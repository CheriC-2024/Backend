package com.art.cheric.module.collection.domain.repository.custom;

import com.art.cheric.module.art.domain.entity.QArt;
import com.art.cheric.module.art.dto.res.ArtBriefResDto;
import com.art.cheric.module.collection.domain.entity.QCollection;
import com.art.cheric.module.collection.domain.entity.QCollectionArt;
import com.art.cheric.module.collection.dto.res.CollectionArtResDto;
import com.art.cheric.module.collection.dto.res.CollectionResDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


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
    public List<CollectionArtResDto> getCollectionByCollectionIds(long userId, List<Long> collectionIds) {
        QCollection collection = QCollection.collection;
        QCollectionArt collectionArt = QCollectionArt.collectionArt;
        QArt art = QArt.art;

        // 쿼리 실행
        List<Tuple> results = jpaQueryFactory
                .select(
                        collection.id,
                        collection.name,
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
                        .and(collection.id.in(collectionIds))) // 컬렉션 ID 조건
                .orderBy(collection.id.asc(), art.id.asc()) // 컬렉션별로 정렬
                .fetch();

        // 결과 매핑
        return results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(collection.id), // 컬렉션 ID를 기준으로 그룹화
                        Collectors.mapping(tuple -> ArtBriefResDto.of(
                                tuple.get(art.id),
                                tuple.get(art.isCollectorsArt),
                                tuple.get(art.imgUrl),
                                tuple.get(art.cherryPrice),
                                tuple.get(art.name)
                        ), Collectors.toList())
                ))
                .entrySet()
                .stream()
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
                            artBriefResDtos
                    );
                })
                .toList();
    }


}
