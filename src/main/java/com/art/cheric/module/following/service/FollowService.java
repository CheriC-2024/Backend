package com.art.cheric.module.following.service;

import com.art.cheric.global.enums.FollowSortType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.following.domain.entity.Follow;
import com.art.cheric.module.following.domain.repository.FollowRepository;
import com.art.cheric.module.following.error.FollowErrorCode;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.res.UserBrief2ResDto;
import com.art.cheric.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    // 팔로우 추가
    @Transactional
    public void postFollow(User user, Long followedUserId) {
        // 본인 아이디로 요청한 건 아닌지 검증
        checkFollowingSelf(user.getId(), followedUserId);

        // 사용자 있는지 여부 검증
        User followedUser = userService.findUserById(followedUserId);

        // 이미 팔로우한 사용자인지 확인
        checkFollowingUnique(user, followedUserId);

        // 팔로우 설정
        followRepository.save(Follow.of(user, followedUser));

        // 팔로우 수 설정
        user.plusFollowing();
        followedUser.plusFollower();

        // 사용자 저장
        List<User> users = List.of(user, followedUser);
        userService.saveAllUser(users);


        // TODO 해당 사용자에게 알림 날리기
    }

    private static void checkFollowingSelf(Long userId, Long followedUserId) {
        if (Objects.equals(userId, followedUserId)) {
            throw new AppException(FollowErrorCode.INVALID_FOLLOWED_ID);
        }
    }

    private void checkFollowingUnique(User user, Long followedUserId) {
        if (followRepository.findByFollowingUserIdAndFollowedUserId(user.getId(), followedUserId).isPresent()) {
            throw new AppException(FollowErrorCode.FOLLOW_DUPLICATED);
        }
    }

    // 팔로우 삭제
    @Transactional
    public void deleteFollow(User user, Long followedUserId) {
        // 사용자 있는지 여부 검증
        User followedUser = userService.findUserById(followedUserId);

        // 이미 팔로우한 사용자인지 확인
        Follow follow = followRepository.findByFollowingUserIdAndFollowedUserId(user.getId(), followedUserId)
                .orElseThrow(() -> new AppException(FollowErrorCode.DOESNT_FOLLOW_YET));

        // 팔로우 설정
        followRepository.delete(follow);

        // 팔로우 수 설정
        user.minusFollowing();
        followedUser.minusFollower();

        // 사용자 저장
        List<User> users = List.of(user, followedUser);
        userService.saveAllUser(users);
    }

    public List<Long> findFollowingIdsListByUserId(Long userId) {
        return followRepository.findByFollowingUserId(userId).stream().map(
                follow -> follow.getFollowedUser().getId()
        ).toList();
    }


    public Page<UserBrief2ResDto> getFollowList(Long userId, FollowSortType sort, UserOrderType order,
                                                int page, int size) {
        // 사용자 유효성 검증
        userService.findUserById(userId);

        // 페이징 데이터 생성
        Pageable pageable = PageRequest.of(page, size);

        // 팔로잉 필터를 위한 팔로잉 ID 리스트 조회
        List<Long> followingIds = findFollowingIdsListByUserId(userId);

        // 필터링 및 정렬된 데이터 가져오기
        Page<Follow> follows = followRepository.getFollowsByUserIdAndOrderAndPaging(userId, sort, order, pageable);

        // 엔티티를 DTO로 매핑
        List<UserBrief2ResDto> result = follows.stream()
                .map(follow -> mapFollowToUserBrief(follow, sort, followingIds))
                .toList();

        // 페이징된 결과 반환
        return new PageImpl<>(result, pageable, follows.getTotalElements());
    }

    private UserBrief2ResDto mapFollowToUserBrief(Follow follow, FollowSortType sort, List<Long> followingIds) {
        var user = switch (sort) {
            case FOLLOWER -> follow.getFollowingUser();
            case FOLLOWING -> follow.getFollowedUser();
        };

        return UserBrief2ResDto.of(
                user.getId(),
                user.getName(),
                user.getProfileImgUrl(),
                userService.getArtTypes(user.getUserParts()),
                followingIds.contains(user.getId())
        );
    }

}
