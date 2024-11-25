package com.art.cheric.module.exhibition.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.exhibition.dto.req.ArtChatGptReqDto;
import com.art.cheric.module.exhibition.dto.req.ArtCloudReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Exhibition AI", description = "전시 AI 관련 API")
public interface ExhibitionAiControllerDocs {

    @Operation(summary = "작품의 속성 추출 API", description = "Cloud Vision을 이용해 키워드 혹은 색상을 추출합니다.")
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
                                    "{ \"code\": 400, \"message\": \"작품 ID는 한 요청에 한 개만 수용이 가능합니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"지원하는 Cloud Vision Type이 아닙니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"요청한 작품의 이미지 경로가 유효하지 않습니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value =  "[" +
                                    "{ \"code\": 403, \"message\": \"해당 작가의 작품은 이제 사용할 수 없습니다.\" },"+
                                    "{ \"code\": 403, \"message\": \"해당 소장 작품은 현재 인증이 되지 않았습니다.\" },"+
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
                                    "{ \"code\": 404, \"message\": \"해당 작품을 찾을 수 없습니다.\" },"+
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" },"+
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }"+
                                    "]"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> postArtsProperties(@RequestBody @Valid ArtCloudReqDto artCloudReq);

    @Operation(summary = "전시의 테마 및 제목 추출 API", description = "Chat GPT를 통해 제목 혹은 테마를 추출합니다.")
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
    ResponseEntity<ResponseDto> postChatGptResult(@RequestBody @Valid ArtChatGptReqDto artChatGptReq);
}

