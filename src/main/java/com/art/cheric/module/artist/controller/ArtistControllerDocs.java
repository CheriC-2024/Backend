package com.art.cheric.module.artist.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.artist.dto.req.ArtistReqDto;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Artist", description = "작가 관련 API")
public interface ArtistControllerDocs {

    @Operation(summary = "작가 인증 요청 API", description = "작가의 정보를 등록하고 인증을 요청하는 상태가 됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"1 사용자가 작가 인증을 요청하였습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(value = "{ \"code\": 409, \"message\": \"이미 작가 등록 단계(인증 전, 인증 진행 중, 인증 완료 등)에 있는 사용자입니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postArtist(User user, ArtistReqDto artistReq);

    @Operation(summary = " 작가 정보 조회 API", description = "id 로 작가의 정보를 조회합니다. 만약, id가 Null 로 전달될 경우, 본인의 정보를 조회합니다.")
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
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당 사용자는 작가가 아닙니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> getArtist(User user, Long artistUserId);
}

