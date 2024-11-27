package com.art.cheric.module.user.controller;

import com.art.cheric.global.common.DataPageResponseDto;
import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.req.SignUpReqDto;
import com.art.cheric.module.user.dto.res.*;
import com.art.cheric.module.user.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserControllerDocs {
    private final UserService userService;

    @GetMapping("/google-login")
    public ResponseEntity<ResponseDto> getGoogleLogin(@RequestHeader("id-token") String idToken,
                                                      @RequestHeader("device-token") String deviceToken,
                                                      @RequestHeader("fcm-token") String fcmToken) {
        LoginResDto resDto = userService.getGoogleLogin(idToken, fcmToken, deviceToken);
        return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
    }

    @GetMapping("/token")
    public ResponseEntity<ResponseDto> getAccessToken(@RequestHeader("Authorization-Refresh") String refreshToken) {
        LoginResDto resDto = userService.getAccessToken(refreshToken);
        return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> postSignUp(@RequestAttribute("user") User user,
                                                  @RequestBody @Valid SignUpReqDto signUpReq) {
        userService.postSignUp(user, signUpReq);
        return ResponseEntity.ok(ResponseDto.of(200));
    }

    @GetMapping("/name")
    public ResponseEntity<ResponseDto> checkNameIsDuplicated(@RequestParam("name") String name) {
        userService.checkNameIsDuplicated(name);
        return ResponseEntity.ok(ResponseDto.of(200));
    }

    @DeleteMapping("/google-logout")
    public ResponseEntity<ResponseDto> deleteGoogleLogout(@RequestAttribute("user") User user) {
        userService.deleteGoogleLogout(user);
        return ResponseEntity.ok(ResponseDto.of(200));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getUserDetailInfo(@RequestAttribute("user") User user,
                                                         @RequestParam("id") @Nullable Long userId) {
        UserDetailResDto resDto = userService.getUserDetailInfo(user, userId);
        return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
    }

    @GetMapping("/all-brief")
    public ResponseEntity<ResponseDto> getUserBriefList(
            @RequestAttribute("user") User user,
            @Schema(description = "ture라면, 팔로잉 하는 사용자만, false라면, 팔로잉 되어있지 않은 사용자만" +
                    "null이라면  팔로우와 관계없이 조회합니다.") @RequestParam(name = "isFollowing") @Nullable Boolean isFollowing,
            @Schema(description = "false 라면, 컬렉터만 / true 라면 작가만 / null이라면 모두 반환합니다.") @RequestParam(name = "isArtist") @Nullable Boolean isArtist,
            @Schema(description = "해당 관심 분야에 맞는 사용자만 반환합니다.(관심 분야 최대 2개, 3개 이후 무시)") @RequestParam(name = "artTypes") @Nullable List<ArtType> artTypes,
            @RequestParam(name = "order") UserOrderType order,
            @Schema(description = "0번부터 시작합니다. 조회할 페이지 번호를 의미합니다.") @RequestParam(name = "page") int page,
            @Schema(description = "조회할 페이지 크기를 의미합니다.") @RequestParam(name = "size") int size) {

        Page<UserBriefResDto> resPage = userService.getUserBriefList(user, isFollowing, isArtist, artTypes, order, page, size);
        return ResponseEntity.status(200).body(DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }

    @GetMapping("/all-follow")
    public ResponseEntity<ResponseDto> getUserFollowInfoList(
            @RequestAttribute("user") User user,
            @Schema(description = "ture라면, 팔로잉 하는 사용자만, false라면, 팔로잉 되어있지 않은 사용자만" +
                    "null이라면  팔로우와 관계없이 조회합니다.") @RequestParam(name = "isFollowing") @Nullable Boolean isFollowing,
            @Schema(description = "false 라면, 컬렉터만 / true 라면 작가만 / null이라면 모두 반환합니다.") @RequestParam(name = "isArtist") @Nullable Boolean isArtist,
            @Schema(description = "해당 관심 분야에 맞는 사용자만 반환합니다.(관심 분야 최대 2개, 3개 이후 무시)") @RequestParam(name = "artTypes") @Nullable List<ArtType> artTypes,
            @RequestParam(name = "order") UserOrderType order,
            @Schema(description = "0번부터 시작합니다. 조회할 페이지 번호를 의미합니다.") @RequestParam(name = "page") int page,
            @Schema(description = "조회할 페이지 크기를 의미합니다.") @RequestParam(name = "size") int size) {

        Page<UserBrief2ResDto> resPage = userService.getUserFollowInfoList(user, isFollowing, isArtist, artTypes, order, page, size);
        return ResponseEntity.status(200).body(DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }


    @GetMapping("/recommend")
    public ResponseEntity<ResponseDto> getUserRecommend(
            @RequestAttribute("user") User user,
            @RequestParam(name = "artType") ArtType artType,
            @RequestParam(name = "order") UserOrderType order,
            @Schema(description = "0번부터 시작합니다. 조회할 페이지 번호를 의미합니다.") @RequestParam(name = "page") int page,
            @Schema(description = "조회할 페이지 크기를 의미합니다.") @RequestParam(name = "size") int size) {
        List<UserListResDto> resDto = userService.getUserRecommend(user, artType, order, page, size);
        return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));

    }

}
