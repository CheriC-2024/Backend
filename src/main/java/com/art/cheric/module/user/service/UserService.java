package com.art.cheric.module.user.service;

import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.enums.JwtVo;
import com.art.cheric.global.error.ErrorCode;
import com.art.cheric.global.error.GlobalErrorCode;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.global.util.GoogleOAuthUtil;
import com.art.cheric.global.util.JwtUtil;
import com.art.cheric.global.util.RedisUtil;
import com.art.cheric.module.artist.dto.req.ArtistBasicReqDto;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.domain.entity.UserPart;
import com.art.cheric.module.user.domain.repository.UserPartRepository;
import com.art.cheric.module.user.domain.repository.UserRepository;
import com.art.cheric.module.user.dto.req.SignUpReqDto;
import com.art.cheric.module.user.dto.res.LoginResDto;
import com.art.cheric.module.user.error.UserErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void checkNameIsDuplicated(String name) {
        checkNameIsBlankOrSizeError(name);
        checkArtistNameIsDuplicated(name);
    }

    private void checkNameIsBlankOrSizeError(String name) {
        if (name.isBlank()) {
            throw new AppException(UserErrorCode.NAME_REQUIRED);
        } else if (!(name.length() >= 2 && name.length() <= 10)) {
            throw new AppException(UserErrorCode.NAME_SIZE_ERROR);
        }
    }

    private void checkArtistNameIsDuplicated(String name) {
        userRepository.findByIsValidateArtistFalseAndName(name).ifPresent(user -> {
            throw new AppException(UserErrorCode.NAME_DUPLICATED);
        });
    }

    @Transactional
    public void deleteGoogleLogout(User user) {
        // 사용자 refreshToken 삭제
        redisUtil.delete(user.getId() + "_refresh");
    }

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

}
