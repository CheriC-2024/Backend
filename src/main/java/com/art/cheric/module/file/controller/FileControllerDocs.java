package com.art.cheric.module.file.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.file.dto.req.PresignedUrlReqDto;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "File", description = "파일 관련 API")
public interface FileControllerDocs {

    @Operation(summary = "PresignedUrl 생성", description = "presignedUrl을 생성합니다.")
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
                                    "{ \"code\": 400, \"message\": \"유효한 파일 이름이 아닙니다. 파일 이름에 문자, 숫자, _, - 이외의 값을 넣지 마세요\" }," +
                                    "{ \"code\": 400, \"message\": \"파일 확장자를 같이 보내주세요\" }," +
                                    "{ \"code\": 400, \"message\": \"유효한 파일 확장자가 아닙니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"파일 이름 길이가 허용 범위보다 깁니다.\" }," +
                                    "{ \"code\": 400, \"message\": \"파일 사이즈가 허용 크기보다 큽니다.\" }," +
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
            )
    })
    ResponseEntity<ResponseDto> getPresignedUrl(User user, PresignedUrlReqDto presignedUrl);

}

