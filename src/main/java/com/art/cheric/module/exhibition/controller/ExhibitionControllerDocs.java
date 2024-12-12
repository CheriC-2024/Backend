package com.art.cheric.module.exhibition.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.ExhibitionOrderType;
import com.art.cheric.module.exhibition.dto.req.ExhibitionReqDto;
import com.art.cheric.module.exhibition.dto.req.ExhibitionReviewReqDto;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Exhibition", description = "전시 관련 API")
public interface ExhibitionControllerDocs {

    @Operation(summary = "전시 생성 API", description = "전시를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"1 전시가 생성되었습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 400, \"message\": \"전시의 배경은 이미지 혹은 색상으로 설정되어야 합니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> postExhibition(User user, ExhibitionReqDto exhibitionReq);

    @Operation(summary = "전시 내용 조회 API", description = "전시를 내용을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n" +
                                    "  \"code\": 200,\n" +
                                    "  \"message\": \"OK\",\n" +
                                    "  \"data\": {\n" +
                                    "    \"name\": \"별빛:하늘을 그리다\",\n" +
                                    "    \"font\": \"BASIC\",\n" +
                                    "    \"fontColor\": \"BLACK\",\n" +
                                    "    \"description\": \"별빛을 나타내는 작품을 모은 전시입니다.\",\n" +
                                    "    \"heartCount\": 0,\n" +
                                    "    \"hits\": 0,\n" +
                                    "    \"exhibitionArtRess\": [\n" +
                                    "      {\n" +
                                    "        \"description\": \"이러쿵 저러쿵을 통해 수집하게 되었습니다.\",\n" +
                                    "        \"reasonForPurchase\": \"별빛을 나타내는 게 마음에 와닿아서 수집하게 되었습니다.\",\n" +
                                    "        \"review\": \"별빛을 나타내는 작품을 보는 과정에서 행복했습니다.\",\n" +
                                    "        \"artExhibitionRes\": {\n" +
                                    "          \"imgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n" +
                                    "          \"cherryPrice\": null,\n" +
                                    "          \"name\": \"별이 빛나는 밤에\",\n" +
                                    "          \"artistName\": \"이작가\",\n" +
                                    "          \"series\": \"별\",\n" +
                                    "          \"horizontalSize\": 100000,\n" +
                                    "          \"verticalSize\": 200000,\n" +
                                    "          \"material\": \"수채화 물감을 사용했습니다.\",\n" +
                                    "          \"madeAt\": \"2024\",\n" +
                                    "          \"artTypes\": [\n" +
                                    "            \"PAINTING\",\n" +
                                    "            \"OIL_PAINTING\"\n" +
                                    "          ],\n" +
                                    "          \"ownArtRes\": {\n" +
                                    "            \"price\": 100000\n" +
                                    "          },\n" +
                                    "          \"heartCount\": 0,\n" +
                                    "          \"collectorsArt\": true\n" +
                                    "        }\n" +
                                    "      }\n" +
                                    "    ],\n" +
                                    "    \"userRes\": {\n" +
                                    "      \"id\": 1,\n" +
                                    "      \"name\": \"yerim\",\n" +
                                    "      \"description\": \"저는 3년차 회화 분야 작가입니다.\",\n" +
                                    "      \"artTypes\": [\n" +
                                    "        \"PAINTING\",\n" +
                                    "        \"WATER_PAINTING\"\n" +
                                    "      ],\n" +
                                    "      \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\"\n" +
                                    "    },\n" +
                                    "    \"exhibitionReviewRes\": null\n" +
                                    "  }\n" +
                                    "}")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 전시가 존재하지 않습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
    })
    ResponseEntity<ResponseDto> getExhibitionContent(User user, Long exhibitionId);

    @Operation(summary = "전시 하트 추가 API", description = "전시에 하트를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 200, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 전시가 존재하지 않습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"이미 해당 전시에 하트 표시를 하셨습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postHeart(User user, Long exhibitionId);

    @Operation(summary = "전시 하트 취소 API", description = "전시에 하트를 취소합니다.")
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
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 전시가 존재하지 않습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"해당 전시에 하트 표시를 한 적이 없어 취소할 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> deleteHeart(User user, Long exhibitionId);

    @Operation(summary = "전시 댓글 생성 API", description = "전시에 댓글을 답니다.")
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
    ResponseEntity<ResponseDto> postReview(User user, Long exhibitionId, ExhibitionReviewReqDto exhibitionReviewReq);

    @Operation(summary = "전시 대댓글 생성 API", description = "전시에 댓글을 답니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 전시 댓글이 존재하지 않습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> postReReview(User user, Long exhibitionId, Long reviewId,
                                           ExhibitionReviewReqDto exhibitionReviewReq);

    @Operation(summary = "전시 댓글 좋아요 생성 API", description = "전시에 댓글에 좋아요를 답니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 전시 댓글이 존재하지 않습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"이미 해당 댓글에 하트 표시를 하셨습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> postReviewHeart(User user, Long exhibitionId, Long reviewId);

    @Operation(summary = "전시 댓글 좋아요 취소 API", description = "전시에 댓글에 좋아요를 취소합니다.")
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
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 전시 댓글이 존재하지 않습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"해당 댓글에 하트 표시를 한 적이 없어 취소할 수 없습니다.\" }," +
                                    "]"
                            )
                    )
            ),
    })
    ResponseEntity<ResponseDto> deleteReviewHeart(User user, Long exhibitionId, Long reviewId);

    @Operation(summary = "전시 조회수 증가 API", description = "전시 조회수를 증가시킵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 전시가 존재하지 않습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> postHits(User user, Long exhibitionId);


    @Operation(summary = "전시 리스트 조회 API", description = "전시 리스트를 조건에 맞춰 조회합니다.")
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
                                    "      \"exhibitionId\": 2,\n" +
                                    "      \"name\": \"별빛:하늘을 그리다\",\n" +
                                    "      \"font\": \"BASIC\",\n" +
                                    "      \"fontColor\": \"BLACK\",\n" +
                                    "      \"colors\": [],\n" +
                                    "      \"exhibitionBackgroundType\": null,\n" +
                                    "      \"coverImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/EXHIBITION_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-exhibitionImage.png\",\n" +
                                    "      \"themes\": [\n" +
                                    "        \"별\",\n" +
                                    "        \"하늘\",\n" +
                                    "        \"빛\"\n" +
                                    "      ],\n" +
                                    "      \"heartCount\": 0,\n" +
                                    "      \"hits\": 0,\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"name\": \"artist\",\n" +
                                    "        \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\"\n" +
                                    "      }\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"exhibitionId\": 1,\n" +
                                    "      \"name\": \"별빛:하늘을 그리다\",\n" +
                                    "      \"font\": \"BASIC\",\n" +
                                    "      \"fontColor\": \"BLACK\",\n" +
                                    "      \"colors\": [\n" +
                                    "        \"#CF3420\",\n" +
                                    "        \"#CF3421\",\n" +
                                    "        \"#CF3422\",\n" +
                                    "        \"#CF3423\"\n" +
                                    "      ],\n" +
                                    "      \"exhibitionBackgroundType\": \"TOP_DOWN\",\n" +
                                    "      \"coverImgUrl\": null,\n" +
                                    "      \"themes\": [\n" +
                                    "        \"별\",\n" +
                                    "        \"하늘\",\n" +
                                    "        \"빛\"\n" +
                                    "      ],\n" +
                                    "      \"heartCount\": 0,\n" +
                                    "      \"hits\": 0,\n" +
                                    "      \"userRes\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"name\": \"artist\",\n" +
                                    "        \"profileImgUrl\": \"https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/USER_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-profileimage.png\"\n" +
                                    "      }\n" +
                                    "    }\n" +
                                    "  ],\n" +
                                    "  \"totalElements\": 2,\n" +
                                    "  \"totalPages\": 1,\n" +
                                    "  \"size\": 3,\n" +
                                    "  \"numberOfElements\": 2\n" +
                                    "}")
                    )
            ),
    })
    ResponseEntity<ResponseDto> getExhibitions(Long artId, Long userId, ExhibitionOrderType order, int page, int size);

    @Operation(summary = "전시 댓글 리스트 조회 API", description = "전시의 대댓글 제외 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n"
                                    + "  \"code\": 200,\n"
                                    + "  \"message\": \"OK\",\n"
                                    + "  \"data\": [\n"
                                    + "    {\n"
                                    + "      \"id\": 4,\n"
                                    + "      \"review\": \"3수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "      \"name\": null,\n"
                                    + "      \"heartCount\": 0,\n"
                                    + "      \"replyCount\": 0,\n"
                                    + "      \"createAt\": \"2024.11.27\"\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"id\": 3,\n"
                                    + "      \"review\": \"2수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "      \"name\": null,\n"
                                    + "      \"heartCount\": 0,\n"
                                    + "      \"replyCount\": 0,\n"
                                    + "      \"createAt\": \"2024.11.27\"\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"id\": 2,\n"
                                    + "      \"review\": \"1수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "      \"name\": null,\n"
                                    + "      \"heartCount\": 0,\n"
                                    + "      \"replyCount\": 0,\n"
                                    + "      \"createAt\": \"2024.11.27\"\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"id\": 1,\n"
                                    + "      \"review\": \"수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "      \"name\": null,\n"
                                    + "      \"heartCount\": 0,\n"
                                    + "      \"replyCount\": 2,\n"
                                    + "      \"createAt\": \"2024.11.27\"\n"
                                    + "    }\n"
                                    + "  ],\n"
                                    + "  \"totalElements\": 4,\n"
                                    + "  \"totalPages\": 1,\n"
                                    + "  \"size\": 4,\n"
                                    + "  \"numberOfElements\": 4\n"
                                    + "}")
                    )
            ),
    })
    ResponseEntity<ResponseDto> getExhibitionReviews( User user, Long exhibitionId, int page,int size);

    @Operation(summary = "전시 댓글 상세 조회 API", description = "전시 및 댓글 id에 해당하는 댓글과 대댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n"
                                    + "  \"code\": 200,\n"
                                    + "  \"message\": \"OK\",\n"
                                    + "  \"data\": {\n"
                                    + "    \"id\": 1,\n"
                                    + "    \"review\": \"수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "    \"name\": null,\n"
                                    + "    \"heartCount\": 0,\n"
                                    + "    \"createAt\": \"2024.11.27\",\n"
                                    + "    \"replies\": [\n"
                                    + "      {\n"
                                    + "        \"id\": 5,\n"
                                    + "        \"review\": \"1수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "        \"name\": null,\n"
                                    + "        \"heartCount\": 0,\n"
                                    + "        \"createAt\": \"2024.11.27\",\n"
                                    + "        \"replies\": [\n"
                                    + "          {\n"
                                    + "            \"id\": 7,\n"
                                    + "            \"review\": \"1수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "            \"name\": null,\n"
                                    + "            \"heartCount\": 0,\n"
                                    + "            \"createAt\": \"2024.11.27\",\n"
                                    + "            \"replies\": []\n"
                                    + "          },\n"
                                    + "          {\n"
                                    + "            \"id\": 8,\n"
                                    + "            \"review\": \"2수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "            \"name\": null,\n"
                                    + "            \"heartCount\": 0,\n"
                                    + "            \"createAt\": \"2024.11.27\",\n"
                                    + "            \"replies\": [\n"
                                    + "              {\n"
                                    + "                \"id\": 9,\n"
                                    + "                \"review\": \"1수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "                \"name\": null,\n"
                                    + "                \"heartCount\": 0,\n"
                                    + "                \"createAt\": \"2024.11.27\",\n"
                                    + "                \"replies\": []\n"
                                    + "              }\n"
                                    + "            ]\n"
                                    + "          }\n"
                                    + "        ]\n"
                                    + "      },\n"
                                    + "      {\n"
                                    + "        \"id\": 6,\n"
                                    + "        \"review\": \"2수집하신 소장 작품 너무 좋네요!\",\n"
                                    + "        \"name\": null,\n"
                                    + "        \"heartCount\": 0,\n"
                                    + "        \"createAt\": \"2024.11.27\",\n"
                                    + "        \"replies\": []\n"
                                    + "      }\n"
                                    + "    ]\n"
                                    + "  }\n"
                                    + "}")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 전시 댓글이 존재하지 않습니다.\" }")
                    )
            ),
    })
    ResponseEntity<ResponseDto> getExhibitionReviews(User user, Long exhibitionId, Long reviewId);
}

