package com.art.cheric.module.art.domain.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
@Slf4j
public class ArtRepositoryImpl implements ArtRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
