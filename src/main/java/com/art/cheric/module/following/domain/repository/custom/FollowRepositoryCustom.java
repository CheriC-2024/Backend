package com.art.cheric.module.following.domain.repository.custom;

import com.art.cheric.global.enums.FollowSortType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.following.domain.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepositoryCustom {
    Optional<Follow> findByFollowingUserIdAndFollowedUserId(Long followingUserId, Long followedUserId);

    List<Follow> findByFollowingUserId(Long followingUserId);

    Page<Follow> getFollowsByUserIdAndOrderAndPaging(Long userId, FollowSortType sort, UserOrderType order, Pageable pageable);

}
