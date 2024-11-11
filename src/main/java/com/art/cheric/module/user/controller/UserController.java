package com.art.cheric.module.user.controller;

import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.req.SignUpReqDto;
import com.art.cheric.module.user.dto.res.LoginResDto;
import com.art.cheric.module.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
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
}
