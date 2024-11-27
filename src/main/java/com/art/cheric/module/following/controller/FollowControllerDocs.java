package com.art.cheric.module.following.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.FollowSortType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Follow", description = "팔로우 관련 API")
public interface FollowControllerDocs {


    @Operation(summary = "팔로잉 추가 API", description = "팔로잉 합니다.")
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
                            examples =
                            @ExampleObject(value = "{ \"code\": 400, \"message\": \"자신을 팔로우할 수 없습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"이미 해당 사용자를 팔로우 중입니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postFollow(User user, Long followedUserId);

    @Operation(summary = "팔로잉 삭제 API", description = "팔로잉 취소합니다.")
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
                            examples =
                            @ExampleObject(value = "{ \"code\": 400, \"message\": \"아직 팔로우 하지 않아, 팔로우를 취소할 수 없습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> deleteFollow(User user, Long followedUserId);

    @Operation(summary = "팔로잉/팔로워 리스트 조회 API", description = "팔로잉/팔로우 리스트를 조회합니다.")
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
                                    "      \"id\": 2,\n" +
                                    "      \"name\": \"test1\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "      \"artTypes\": [\n" +
                                    "        \"ORIENTAL_PAINTING\",\n" +
                                    "        \"DESIGN_ART\"\n" +
                                    "      ],\n" +
                                    "      \"following\": true\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 3,\n" +
                                    "      \"name\": \"test2\",\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "      \"artTypes\": [\n" +
                                    "        \"WATER_PAINTING\",\n" +
                                    "        \"OIL_PAINTING\"\n" +
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
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> getFollowList(Long userId, FollowSortType sort, UserOrderType order, int page, int size);
}

