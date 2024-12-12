package com.art.cheric.module.following.domain.repository.custom;

import com.art.cheric.global.enums.FollowSortType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.following.domain.entity.Follow;
import com.art.cheric.module.following.domain.entity.QFollow;
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
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Follow> findByFollowingUserIdAndFollowedUserId(Long followingUserId, Long followedUserId) {
        QFollow follow = QFollow.follow;
        Follow result = jpaQueryFactory.selectFrom(follow)
                .where(
                        follow.followingUser.id.eq(followingUserId)
                                .and(follow.followedUser.id.eq(followedUserId))
                )
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Follow> findByFollowingUserId(Long followingUserId) {
        QFollow follow = QFollow.follow;
        return jpaQueryFactory.selectFrom(follow)
                .where(follow.followingUser.id.eq(followingUserId))
                .fetch();
    }

    @Override
    public Page<Follow> getFollowsByUserIdAndOrderAndPaging(Long userId, FollowSortType sort, UserOrderType order, Pageable pageable) {
        QFollow follow = QFollow.follow;

        // 정렬 조건 설정
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifier(sort, order, follow);

        // 조건 설정
        BooleanExpression followCondition = switch (sort) {
            case FOLLOWING -> follow.followingUser.id.eq(userId);
            case FOLLOWER -> follow.followedUser.id.eq(userId);
        };

        // 데이터 조회
        List<Follow> results = jpaQueryFactory
                .selectFrom(follow)
                .where(followCondition)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // 전체 데이터 개수 조회
        long total = jpaQueryFactory
                .select(follow.count())
                .from(follow)
                .where(followCondition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifier(FollowSortType sort, UserOrderType order, QFollow follow) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        switch (order) {
            case LATEST -> orderSpecifiers.add(follow.createdAt.desc());
            case NAME -> {
                switch (sort) { // 가져갈 리스트 종류에 따라
                    case FOLLOWER -> orderSpecifiers.add(follow.followingUser.name.asc());
                    case FOLLOWING -> orderSpecifiers.add(follow.followedUser.name.asc());
                }
            }
            case FOLLOWER -> {
                switch (sort) { // 가져갈 리스트 종류에 따라
                    case FOLLOWER -> orderSpecifiers.add(follow.followingUser.followerAmount.asc());
                    case FOLLOWING -> orderSpecifiers.add(follow.followedUser.followerAmount.asc());
                }
            }
        }

        if (order != UserOrderType.LATEST) {
            orderSpecifiers.add(follow.createdAt.desc());
        }
        return orderSpecifiers;
    }
}
