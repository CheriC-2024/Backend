package com.art.cheric.module.user.service;

import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.enums.JwtVo;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.global.error.ErrorCode;
import com.art.cheric.global.error.GlobalErrorCode;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.global.util.GoogleOAuthUtil;
import com.art.cheric.global.util.JwtUtil;
import com.art.cheric.global.util.RedisUtil;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.repository.ArtRepository;
import com.art.cheric.module.art.dto.res.ArtBriefResDto;
import com.art.cheric.module.artist.dto.req.ArtistBasicReqDto;
import com.art.cheric.module.following.domain.repository.FollowRepository;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.domain.entity.UserPart;
import com.art.cheric.module.user.domain.repository.UserPartRepository;
import com.art.cheric.module.user.domain.repository.UserRepository;
import com.art.cheric.module.user.dto.req.SignUpReqDto;
import com.art.cheric.module.user.dto.res.ExhibitionUserResDto;
import com.art.cheric.module.user.dto.res.HotUserListResDto;
import com.art.cheric.module.user.dto.res.LoginResDto;
import com.art.cheric.module.user.dto.res.UserBrief2ResDto;
import com.art.cheric.module.user.dto.res.UserBriefResDto;
import com.art.cheric.module.user.dto.res.UserDetailResDto;
import com.art.cheric.module.user.dto.res.UserListResDto;
import com.art.cheric.module.user.dto.res.UserResDto;
import com.art.cheric.module.user.error.UserErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final GoogleOAuthUtil googleOAuthUtil;
    private final UserRepository userRepository;
    private final UserPartRepository userPartRepository;
    private final FollowRepository followRepository;
    private final ArtRepository artRepository;

    // 로그인
    @Transactional
    public LoginResDto getGoogleLogin(String idToken, String fcmToken, String deviceToken) {
        // 요청 인자 유효성 검사
        if (idToken.isBlank()) {
            throw new AppException(UserErrorCode.ID_TOKEN_REQUIRED);
        } else if (fcmToken.isBlank()) {
            throw new AppException(UserErrorCode.FCM_TOKEN_REQUIRED);
        } else if (deviceToken.isBlank()) {
            throw new AppException(UserErrorCode.DEVICE_TOKEN_REQUIRED);
        }

        // 구글 인증 진행
        User googleUser = null;
        try {
            googleUser = googleOAuthUtil.authenticate(idToken, fcmToken, deviceToken);
        } catch (GeneralSecurityException | IOException e) {
            throw new AppException(UserErrorCode.INVALID_ID_TOKEN);
        }

        // 유저 찾고, 없다면 새 유저 생성
        User finalGoogleUser = googleUser;
        User user = userRepository.findByEmail(googleUser.getEmail())
                .orElseGet(() -> userRepository.save(finalGoogleUser));

        // JWT 토큰 생성 및 refreshToken 저장
        JwtVo jwtVo = jwtUtil.generateTokens(user);
        redisUtil.setOpsForValue(user.getId() + "_refresh", jwtVo.getRefreshToken(),
                jwtUtil.getREFRESH_TOKEN_EXPIRATION());

        return LoginResDto.of(jwtVo.getAccessToken(), jwtVo.getRefreshToken(), user.getInfo() == null);
    }

    // refreshToken을 통한 accessToken 생성
    @Transactional
    public LoginResDto getAccessToken(String refreshToken) {
        if (refreshToken.isBlank()) {
            throw new AppException(GlobalErrorCode.REFRESH_TOKEN_REQUIRED);
        }

        // refreshToken 유효성 검사 실행
        User tokenUser;
        try {
            tokenUser = jwtUtil.validateToken(false, refreshToken);
        } catch (JwtException e) {
            ErrorCode code =
                    e instanceof ExpiredJwtException ? GlobalErrorCode.EXPIRED_JWT : GlobalErrorCode.INVALID_TOKEN;

            throw new AppException(code);
        }

        // JWT 토큰 생성 및 refreshToken 저장
        JwtVo jwtVo = jwtUtil.generateTokens(tokenUser);
        redisUtil.setOpsForValue(tokenUser.getId() + "_refresh", jwtVo.getRefreshToken(),
                jwtUtil.getREFRESH_TOKEN_EXPIRATION());

        return LoginResDto.of(jwtVo.getAccessToken(), jwtVo.getRefreshToken(), tokenUser.getInfo() == null);
    }

    // 사용자 회원가입
    @Transactional
    public void postSignUp(User user, SignUpReqDto signUpReq) {
        // 사용자 정보 업데이트
        user.updateUserDetail(signUpReq.name(), signUpReq.info(), signUpReq.profileImgUrl(),
                signUpReq.backgroundImgUrl(), signUpReq.haveExperience(), signUpReq.isArtist());
        userRepository.save(user);

        // 사용자 분야 추가
        for (ArtType artType : signUpReq.userPartRequests()) {
            userPartRepository.save(UserPart.of(user, artType));
        }

    }

    // 사용자 이름 중복 검사
    public void checkNameIsDuplicated(String name) {
        checkNameIsBlankOrSizeError(name);
        checkArtistNameIsDuplicated(name);
    }

    // 사용자 로그아웃
    @Transactional
    public void deleteGoogleLogout(User user) {
        // 사용자 refreshToken 삭제
        redisUtil.delete(user.getId() + "_refresh");
    }

    // 사용자 상세 정보 조회
    public UserDetailResDto getUserDetailInfo(User user, Long userId) {
        User finalUser = user;
        if (userId != null) {
            finalUser = findUserById(userId);
        }

        // 해당 사용자를 follow 중인지 확인하기 위한 리스트 불러오기
        List<Long> followingIds = findFollowingIdsListByUserId(user.getId());

        return UserDetailResDto.of(
                finalUser.getId(),
                finalUser.isValidateArtist(),
                finalUser.getName(),
                finalUser.getInfo(),
                getArtTypes(finalUser.getUserParts()),
                finalUser.getProfileImgUrl(),
                finalUser.getBackgroundImgUrl(),
                finalUser.getFollowerAmount(),
                finalUser.getFollowingAmount(),
                finalUser.getMyCherryNum(),
                finalUser.getSoldCherryNum(),
                followingIds.contains(finalUser.getId())
        );
    }

    // 사용자 간단 정보 리스트 조회
    public Page<UserBriefResDto> getUserBriefList(User user, Boolean isFollowing, Boolean isArtist,
                                                  List<ArtType> artTypes,
                                                  UserOrderType order, int page, int size) {

        // 페이징 데이터 전달
        Pageable pageable = PageRequest.of(page, size);

        // 팔로잉 필터라면 팔로잉 id 전달
        List<Long> followingIds = findFollowingIdsListByUserId(user.getId());

        // 필터링, 정렬에 따른 데이터 가져오기
        Page<User> users = userRepository.getUsersBySortAndFilterAndPaging(isFollowing,
                isFollowing != null ? followingIds : null, isArtist, artTypes,
                order, pageable);

        // 엔티티 dto 매핑
        List<UserBriefResDto> result = users.stream().map(
                userItem -> UserBriefResDto.of(
                        userItem.getId(),
                        userItem.getName(),
                        userItem.getProfileImgUrl()
                )).toList();

        // 페이징된 결과를 반환
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }

    // 사용자 간단 정보 + follow 여부 정보 리스트 조회
    public Page<UserBrief2ResDto> getUserFollowInfoList(User user, Boolean isFollowing, Boolean isArtist,
                                                        List<ArtType> artTypes,
                                                        UserOrderType order, int page, int size) {
        // 페이징 데이터 전달
        Pageable pageable = PageRequest.of(page, size);

        // 팔로잉 필터라면 팔로잉 id 전달
        List<Long> followingIds = findFollowingIdsListByUserId(user.getId());

        // 필터링, 정렬에 따른 데이터 가져오기
        Page<User> users = userRepository.getUsersBySortAndFilterAndPaging(isFollowing,
                isFollowing != null ? followingIds : null, isArtist, artTypes,
                order, pageable);

        // 엔티티 dto 매핑
        List<UserBrief2ResDto> result = users.stream().map(
                userItem -> UserBrief2ResDto.of(
                        userItem.getId(),
                        userItem.getName(),
                        userItem.getProfileImgUrl(),
                        getArtTypes(userItem.getUserParts()),
                        followingIds.contains(userItem.getId())
                )).toList();

        // 페이징된 결과를 반환
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }

    // 사용자 추천
    public List<UserListResDto> getUserRecommend(User user, ArtType artTypes, Boolean isArtist,
                                                 UserOrderType order, int page, int size) {
        // 페이징 데이터 전달
        Pageable pageable = PageRequest.of(page, size);

        // 팔로잉 필터라면 팔로잉 id 전달 + 본인 id 제외 > 추천이기에
        List<Long> followingIds = new ArrayList<>(findFollowingIdsListByUserId(user.getId()));
        followingIds.add(user.getId());

        // 필터링, 정렬에 따른 데이터 가져오기
        Page<User> users = userRepository.getUsersBySortAndFilterAndPaging(false,
                followingIds, isArtist, List.of(artTypes), order, pageable);

        // 엔티티 dto 매핑
        return users.stream().map(
                userItem -> UserListResDto.of(
                        userItem.getId(),
                        userItem.getName(),
                        userItem.getProfileImgUrl(),
                        artRepository.getArtsByUserIdAndCollectorsArtFalseOrderByCreatedAtDesc(userItem.getId())
                                .stream().map(
                                        // TODO 작품 유효성 검사 필요
                                        art -> ArtBriefResDto.of(
                                                art.getId(),
                                                art.isCollectorsArt(),
                                                art.getImgUrl(),
                                                art.getCherryPrice(),
                                                art.getName())
                                ).toList())
        ).toList();

    }

    // hot 한 작가 추천
    public Page<HotUserListResDto> getHotUser(Boolean isArtist, int page, int size) {
        // 페이징 데이터 전달
        Pageable pageable = PageRequest.of(page, size);

        // 필터링, 정렬에 따른 데이터 가져오기
        Page<User> users = userRepository.getUsersBySortAndFilterAndPaging(null, null, isArtist,
                null, UserOrderType.FOLLOWER, pageable);

        // 엔티티 dto 매핑
        List<HotUserListResDto> result = users.stream().map(
                userItem -> {

                    Pageable pageableArt = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("createdAt")));
                    List<Art> arts = artRepository.findMostRecentArtByUserId(userItem.getId(), pageableArt);
                    Art art = arts.isEmpty() ? null : arts.get(0);

                    // TODO 작품 유효성 검사 필요

                    return HotUserListResDto.of(
                            userItem.getId(),
                            userItem.getName(),
                            userItem.getProfileImgUrl(),
                            getArtTypes(userItem.getUserParts()),
                            userItem.getInfo(),
                            art != null ? art.getImgUrl() : null
                    );
                }).toList();

        // 페이징된 결과를 반환
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }

    // 이름 유효성 검사
    private void checkNameIsBlankOrSizeError(String name) {
        if (name.isBlank()) {
            throw new AppException(UserErrorCode.NAME_REQUIRED);
        } else if (!(name.length() >= 2 && name.length() <= 10)) {
            throw new AppException(UserErrorCode.NAME_SIZE_ERROR);
        }
    }

    // 이름 중복 검사
    private void checkArtistNameIsDuplicated(String name) {
        userRepository.findByIsValidateArtistFalseAndName(name).ifPresent(user -> {
            throw new AppException(UserErrorCode.NAME_DUPLICATED);
        });
    }

    // id 기반 사용자 조회
    public User findUserById(Long userId) {
        return userRepository.findById(userId.toString()).orElseThrow(
                () -> new AppException(GlobalErrorCode.USER_NOT_FOUND)
        );
    }

    // 사용자 기본 dto 생성
    public UserResDto createUserResDto(User user) {
        return UserResDto.of(
                user.getId(),
                user.getName(),
                user.getInfo(),
                getArtTypes(user.getUserParts()),
                user.getProfileImgUrl()
        );
    }

    // 전시 제작자 기본 dto 생성
    public ExhibitionUserResDto createExhibitionUserResDto(User user,Long userId) {
        return ExhibitionUserResDto.of(
                user.getId(),
                user.getName(),
                user.getInfo(),
                getArtTypes(user.getUserParts()),
                user.getProfileImgUrl(),
                followRepository.findByFollowingUserIdAndFollowedUserId(userId, user.getId()).isPresent()
        );
    }

    // 사용자 선호 분야 ArtType 으로 가져오기
    private List<ArtType> getArtTypes(List<UserPart> userParts) {
        return userParts.stream()
                .map(UserPart::getUserArtType)
                .collect(Collectors.toList());
    }

    // 유저 모두 저장
    public void saveAllUser(List<User> user) {
        userRepository.saveAll(user);
    }

    // 작가 정보로 사용자 업데이트
    public void updateUserAsArtist(User user, ArtistBasicReqDto artistBasicReq) {
        List<UserPart> userParts = new ArrayList<>();
        for (ArtType artType : artistBasicReq.userPartRequests()) {
            userParts.add(UserPart.of(user, artType));
        }

        user.updateUserDetailAsArtist(
                artistBasicReq.name(),
                artistBasicReq.info(),
                artistBasicReq.profileImgUrl(),
                userParts
        );

        userRepository.save(user);
    }

    // 사용자 분야 조회
    public List<UserPart> getUserPartListByUserId(Long userId) {
        return userPartRepository.findByUserId(userId);
    }

    // 사용자 팔로잉 리스트 조회
    private List<Long> findFollowingIdsListByUserId(Long userId) {
        return followRepository.findByFollowingUserId(userId).stream().map(
                follow -> follow.getFollowedUser().getId()
        ).toList();
    }

}
