package com.art.cheric.module.exhibition.controller;

import com.art.cheric.global.common.ResponseDto;
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
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
    })
    ResponseEntity<ResponseDto> getExhibitionContent(Long exhibitionId);

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
    ResponseEntity<ResponseDto> postReview(User user, Long exhibitionId, Long reviewId,
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


}

