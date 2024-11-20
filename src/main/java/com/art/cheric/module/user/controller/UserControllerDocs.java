package com.art.cheric.module.user.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.req.SignUpReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserControllerDocs {

    @Operation(summary = "로그인", description = "구글 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 400, \"message\": \"Id Token이 필요합니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"Device Id가 필요합니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"Fcm Token이 필요합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> getGoogleLogin(String idToken, String deviceToken, String fcmToken);

    @Operation(summary = "accessToken 발급", description = "리프레시 토큰을 이용해 엑세스 토큰을 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Refresh Token이 필요합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> getAccessToken(String refreshToken);

    @Operation(summary = "회원가입", description = "사용자 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 400, \"message\": \"사용자 이름은 필수 값입니다.\" }, " +
                                    "{ \"code\": 400, \"message\": \"사용자 이름은 2자이상 10자 미만이어야 합니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 소개는 100자 이하여야 합니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 프로필 이미지는 필수 값입니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 컬렉팅 경험 여부는 필수 값입니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 작가 여부는 필수 값입니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 선호 분야는 필수 값입니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 선호 분야는 2개까지 입력 가능합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postSignUp(User user, SignUpReqDto signUpReq);

    @Operation(summary = "컬렉터 사용자 닉네임 중복 검사", description = "컬렉터 사용자 사이의 닉네임 중복 검사를 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 400, \"message\": \"사용자 이름은 필수 값입니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"사용자 이름은 2자이상 10자 미만이어야 합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 409, \"message\": \"기본 사용자의 닉네임은 중복될 수 없습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> checkNameIsDuplicated(String name);

    @Operation(summary = "로그아웃", description = "사용자 로그아웃을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
                    )
            ),
            @ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "[" +
                                    "{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
                                    "{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> deleteGoogleLogout(User user);

}

