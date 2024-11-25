package com.art.cheric.module.collection.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.art.dto.req.ArtIdListReqDto;
import com.art.cheric.module.collection.dto.req.CollectionReqDto;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Collection", description = "컬렉션 관련 API")
public interface CollectionControllerDocs {

    @Operation(summary = "컬렉션 생성 API", description = "컬렉션을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"1 컬렉션이 생성되었습니다.\" }")
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
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"해당 이름을 가진 컬렉션이 이미 있습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postCollection(User user, CollectionReqDto collectionReq);

    @Operation(summary = "컬렉션 작품 추가 API", description = "컬렉션에 작품을 추가합니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
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
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" },"+
                                    "{ \"code\": 404, \"message\": \"해당하는 컬렉션을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"컬렉션 내에 똑같은 작품이 들어갈 수 없습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postCollectionArt(User user, Long collectionId, ArtIdListReqDto collectionArtReq);
}

