package com.art.cheric.module.art.controller;

import com.art.cheric.global.common.ResponseDto;
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
}

