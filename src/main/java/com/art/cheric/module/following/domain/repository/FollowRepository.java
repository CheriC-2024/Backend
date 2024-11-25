package com.art.cheric.module.following.domain.repository;

import com.art.cheric.module.following.domain.entity.Follow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingUserIdAndFollowedUserId(Long followingUserId, Long followedUserId);
}
