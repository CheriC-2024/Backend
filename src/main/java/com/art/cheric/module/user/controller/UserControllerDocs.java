package com.art.cheric.module.user.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.enums.UserOrderType;
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

import java.util.List;

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


    @Operation(summary = "사용자 기본 정보 조회 API", description = "사용자 기본 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> getUserDetailInfo(User user, Long userId);

    @Operation(summary = "사용자 간단 리스트 조회 API", description = "사용자 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"OK\",\n" +
                                    "  \"data\": [\n" +
                                    "    {\n" +
                                    "      \"id\": 3,\n" +
                                    "      \"name\": \"test2\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\"\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 2,\n" +
                                    "      \"name\": \"test1\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\"\n" +
                                    "    }\n" +
                                    "  ],\n" +
                                    "  \"totalElements\": 2,\n" +
                                    "  \"totalPages\": 1,\n" +
                                    "  \"size\": 10,\n" +
                                    "  \"numberOfElements\": 2\n" +
                                    "}")
                    )
            )
    })
    ResponseEntity<ResponseDto> getUserBriefList(
            User user, Boolean isFollowing, Boolean isArtist, List<ArtType> artTypes, UserOrderType order,
            int page, int size);


    @Operation(summary = "사용자 팔로우 여부 포함 리스트 조회 API", description = "사용자 팔로우 여부 포함 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"OK\",\n" +
                                    "  \"data\": [\n" +
                                    "    {\n" +
                                    "      \"id\": 3,\n" +
                                    "      \"name\": \"test2\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "      \"artTypes\": [\n" +
                                    "        \"WATER_PAINTING\",\n" +
                                    "        \"OIL_PAINTING\"\n" +
                                    "      ],\n" +
                                    "      \"following\": true\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 2,\n" +
                                    "      \"name\": \"test1\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "      \"artTypes\": [\n" +
                                    "        \"ORIENTAL_PAINTING\",\n" +
                                    "        \"DESIGN_ART\"\n" +
                                    "      ],\n" +
                                    "      \"following\": true\n" +
                                    "    }\n" +
                                    "  ],\n" +
                                    "  \"totalElements\": 2,\n" +
                                    "  \"totalPages\": 1,\n" +
                                    "  \"size\": 10,\n" +
                                    "  \"numberOfElements\": 2\n" +
                                    "}")
                    )
            )
    })
    ResponseEntity<ResponseDto> getUserFollowInfoList(
            User user, Boolean isFollowing, Boolean isArtist, List<ArtType> artTypes, UserOrderType order,
            int page, int size);

    @Operation(summary = "분야 기반 사용자 추천 리스트 조회 API", description = "조회한 분야를 기반으로 추천하는 컬렉터를 제공합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n" +
                                    "  \"code\": 201,\n" +
                                    "  \"message\": \"Created\",\n" +
                                    "  \"data\": [\n" +
                                    "    {\n" +
                                    "      \"id\": 2,\n" +
                                    "      \"name\": \"test1\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "      \"artBriefRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 24,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "          \"cherryPrice\": 2,\n" +
                                    "          \"collectorsArt\": false\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 23,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "          \"cherryPrice\": 2,\n" +
                                    "          \"collectorsArt\": false\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 22,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "          \"cherryPrice\": 2,\n" +
                                    "          \"collectorsArt\": false\n" +
                                    "        }\n" +
                                    "      ]\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 3,\n" +
                                    "      \"name\": \"test2\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "      \"artBriefRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 20,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "          \"cherryPrice\": 2,\n" +
                                    "          \"collectorsArt\": false\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 19,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "          \"cherryPrice\": 2,\n" +
                                    "          \"collectorsArt\": false\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 18,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "          \"cherryPrice\": 2,\n" +
                                    "          \"collectorsArt\": false\n" +
                                    "        }\n" +
                                    "      ]\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}")
                    )
            )
    })
    ResponseEntity<ResponseDto> getUserRecommend(User user, Boolean isArtist, ArtType artType, UserOrderType order, int page, int size);
}

