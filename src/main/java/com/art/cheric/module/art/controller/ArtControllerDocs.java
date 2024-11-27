package com.art.cheric.module.art.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.ArtOrderType;
import com.art.cheric.global.enums.ArtType;
import com.art.cheric.module.art.dto.req.ArtReqDto;
import com.art.cheric.module.art.dto.req.OwnArtReqDto;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Art", description = "작품 관련 API")
public interface ArtControllerDocs {

    @Operation(summary = "소장 작품 생성 API", description = "컬렉터의 소장 작품을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postOwnArt(User user, OwnArtReqDto ownArtReq);


    @Operation(summary = "작가 작품 생성 API", description = "작가의 작품을 생성합니다.")
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
                            examples = @ExampleObject(value = "{ \"code\": 400, \"message\": \"작가 작품에는 체리가 필수로 들어가져야 합니다.\" }"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> postArtistArt(User user, ArtReqDto artReq);

    @Operation(summary = "작품 상세 확인 API", description = "작품의 상세를 확인합니다.")
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
                                    "{ \"code\": 400, \"message\": \"해당 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"소장 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"작가 작품을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> getArt(User user, Long artId);


    @Operation(summary = "작품 좋아요 추가 API", description = "작품에 좋아요를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 403, \"message\": \"해당 작가의 작품은 이제 사용할 수 없습니다.\" }," +
                                    "{ \"code\": 403, \"message\": \"해당 소장 작품은 현재 인증이 되지 않았습니다.\" }," +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 400, \"message\": \"이미 해당 작품에 하트 표시를 하셨습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> postHeart(User user, Long artId);


    @Operation(summary = "작품 좋아요 취소 API", description = "작품의 좋아요를 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 403, \"message\": \"해당 작가의 작품은 이제 사용할 수 없습니다.\" }," +
                                    "{ \"code\": 403, \"message\": \"해당 소장 작품은 현재 인증이 되지 않았습니다.\" }," +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"해당 작품에 하트 표시를 한 적이 없어 취소할 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> deleteHeart(User user, Long artId);


    @Operation(summary = "소장 작품 소개 가져오기 API", description = "소장 작품의 정보를 가져옵니다.")
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
                            @ExampleObject(value = "{ \"code\": 400, \"message\": \"소장 작품이 아닙니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 403, \"message\": \"해당 소장 작품은 현재 인증이 되지 않았습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"본인 소장 작품을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> getOwnArtDescription(User user, Long artId);

    @Operation(summary = "작품 리스트 API", description = "작품 리스트를 필터,정렬,페이징에 따라 가져옵니다.")
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
                                    "      \"artId\": 24,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 2,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:49\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 23,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 2,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:45\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 22,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 2,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:37\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 21,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 2,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:16\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 20,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:36\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 19,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:33\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 18,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"1 별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:24\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artId\": 17,\n" +
                                    "      \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "      \"name\": \"별이 빛나는 밤에\",\n" +
                                    "      \"cherryNum\": 2,\n" +
                                    "      \"createdAt\": \"artist\",\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"name\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\",\n" +
                                    "        \"profileImgUrl\": \"2024.11.27 08:20\"\n" +
                                    "      }\n" +
                                    "    }\n" +
                                    "  ],\n" +
                                    "  \"totalElements\": 8,\n" +
                                    "  \"totalPages\": 1,\n" +
                                    "  \"size\": 10,\n" +
                                    "  \"numberOfElements\": 8\n" +
                                    "}")
                    )
            )
    })
    ResponseEntity<ResponseDto> getArts(User user, Boolean isFollowing, Long userId, Boolean isCollectorsArt,
                                        ArtType artType, ArtOrderType order, int page, int size);

    @Operation(summary = "작품 분야 별 리스트 API", description = "사용자 선호 분야를 우선순위로 작품 선호 분야를 필터링 합니다.")
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
                                    "      \"artType\": \"회화\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 18,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 17,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 11,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": true\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"유화\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 21,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 20,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 19,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 18,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 17,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 13,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 12,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 11,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": true\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"뉴미디어\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 24,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 23,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 16,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 15,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 14,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": false\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"동양화\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 13,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 12,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": false\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"드로잉\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 23,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 22,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": false\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"디자인\",\n" +
                                    "      \"artMostBriefListRess\": [],\n" +
                                    "      \"userPreference\": false\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"수채화\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 21,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 20,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 19,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": false\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"artType\": \"판화\",\n" +
                                    "      \"artMostBriefListRess\": [\n" +
                                    "        {\n" +
                                    "          \"artId\": 16,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 15,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "          \"artId\": 14,\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\"\n" +
                                    "        }\n" +
                                    "      ],\n" +
                                    "      \"userPreference\": false\n" +
                                    "    }\n" +
                                    "  ],\n" +
                                    "  \"totalElements\": 8,\n" +
                                    "  \"totalPages\": 1,\n" +
                                    "  \"size\": 10,\n" +
                                    "  \"numberOfElements\": 8\n" +
                                    "}")
                    )
            )
    })
    ResponseEntity<ResponseDto> getArtsGroupByArtType(User user, ArtOrderType order, int page, int size);
}

