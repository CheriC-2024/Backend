package com.art.cheric.global.util;

import static com.art.cheric.global.error.GlobalErrorCode.AUTHORIZATION_FAILED;
import static com.art.cheric.global.error.GlobalErrorCode.INVALID_TOKEN;
import static com.art.cheric.global.error.GlobalErrorCode.LOGIN_REQUIRED;
import static com.art.cheric.global.error.GlobalErrorCode.USER_NOT_FOUND;
import static io.jsonwebtoken.Jwts.builder;

import com.art.cheric.global.enums.JwtVo;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {
    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.access-token-expiration}")
    private int ACCESS_TOKEN_EXPIRATION;

    @Getter
    @Value("${jwt.refresh-token-expiration}")
    private int REFRESH_TOKEN_EXPIRATION;

    private final String PAYLOAD_KEY_ID = "id";

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    public JwtVo generateTokens(User user) {
        final String PAYLOAD_KEY_EMAIL = "email";

        // payload에 사용자 식별 값 추가
        Map<String, Object> payloads = new LinkedHashMap<>();
        payloads.put(PAYLOAD_KEY_ID, user.getId());
        payloads.put(PAYLOAD_KEY_EMAIL, user.getEmail());

        // Token 유효기간 설정
        Date now = new Date();
        Date accessExp = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);
        Date refreshExp = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);

        final String ACCESS_SUBJECT = "access";
        final String REFRESH_SUBJECT = "refresh";

        // 액세스 토큰 생성
        String accessToken = builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(payloads)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(accessExp)
                .setSubject(ACCESS_SUBJECT)
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes()))
                .compact();

        // 리프레시 토큰 생성
        String refreshToken = builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(payloads)
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(refreshExp)
                .setSubject(REFRESH_SUBJECT)
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes()))
                .compact();

        return new JwtVo(accessToken, refreshToken);
    }

    // 토큰을 검증하고 유효한 사용자 정보를 반환
    @Transactional(readOnly = true)
    public User validateToken(boolean isAccessToken, String header) throws AppException {
        // Jwt 토큰 추출
        String token = decodeHeader(header);

        // 사용자 정보 추출
        Map<String, Object> payloads = getPayloads(token);

        // 사용자 정보 기반 찾기
        User user = userRepository.findById(String.valueOf(((Number) payloads.get(PAYLOAD_KEY_ID)).longValue()))
                .orElseThrow(() -> new AppException(USER_NOT_FOUND));

        // refreshToken 있는지 확인
        String refresh = redisUtil.getOpsForValue(user.getId() + "_refresh");
        if (refresh == null) { // 저장된 리프레시 토큰이 없을 경우
            throw new AppException(LOGIN_REQUIRED);
        } else if (!isAccessToken && !refresh.equals(token)) { // 리프레시 토큰 요청을 통한 액세스 토큰 발급인 경우
            throw new AppException(AUTHORIZATION_FAILED);
        }

        return user;
    }

    // Authorization 헤더에서 Bearer 토큰을 추출
    private String decodeHeader(String header) {
        final String BEARER = "Bearer ";

        try {
            return Arrays.stream(header.split(BEARER)).toList().get(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AppException(INVALID_TOKEN);
        }
    }

    // JWT 토큰에서 클레임을 추출
    private Map<String, Object> getPayloads(String jwt) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY.getBytes())
                .build();

        Jws<Claims> claimsJws = parser.parseClaimsJws(jwt);

        return claimsJws.getBody();
    }

}
