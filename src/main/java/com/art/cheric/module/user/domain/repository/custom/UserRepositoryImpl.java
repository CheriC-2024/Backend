package com.art.cheric.module.user.domain.repository.custom;

import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.user.domain.entity.QUser;
import com.art.cheric.module.user.domain.entity.User;
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
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 사용자 조회
    @Override
    public Page<User> getUsersBySortAndFilterAndPaging(Boolean isFollowing, List<Long> followingIds, Boolean isArtist, List<ArtType> artTypes,
                                                       UserOrderType order, Pageable pageable) {
        QUser user = QUser.user;

        // 정렬 조건 설정
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifier(order, user);

        // 작가 여부 조건 설정
        BooleanExpression isArtistCondition = isArtist != null ? user.isValidateArtist.eq(isArtist) : null;

        // 미술 분야 조건 설정
        BooleanExpression artTypesCondition = null;
        if (artTypes != null && !artTypes.isEmpty()) {
            artTypesCondition = switch (artTypes.size()) {
                case 1 -> user.userParts.any().userArtType.eq(artTypes.get(0));
                case 2 -> user.userParts.any().userArtType.eq(artTypes.get(0))
                        .and(user.userParts.any().userArtType.eq(artTypes.get(1)));
                default -> null;
            };
        }

        // 팔로우 리스트 여부
        BooleanExpression isFollowingCondition = null;
        if(isFollowing != null){
            if(isFollowing){
                isFollowingCondition = followingIds != null && !followingIds.isEmpty()
                        ? user.id.in(followingIds)
                        : null;
            }else{
                isFollowingCondition = followingIds != null && !followingIds.isEmpty()
                        ? user.id.notIn(followingIds)
                        : null;
            }
        }

        // 데이터 조회
        List<User> results = jpaQueryFactory
                .selectFrom(user)
                .where(isArtistCondition, artTypesCondition, isFollowingCondition)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 데이터 개수 조회
        long total = jpaQueryFactory
                .select(user.count())
                .from(user)
                .where(isArtistCondition, artTypesCondition, isFollowingCondition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }


    private List<OrderSpecifier<?>> getOrderSpecifier(UserOrderType order, QUser user) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        switch (order) {
            case LATEST -> orderSpecifiers.add(user.createdAt.desc());
            case NAME -> orderSpecifiers.add(user.name.asc());
            case FOLLOWER -> orderSpecifiers.add(user.followerAmount.desc());
        }

        if (order != UserOrderType.LATEST) {
            orderSpecifiers.add(user.createdAt.desc());
        }
        return orderSpecifiers;
    }
}
