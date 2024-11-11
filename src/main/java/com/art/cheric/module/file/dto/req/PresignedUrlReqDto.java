package com.art.cheric.module.file.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PresignedUrlReqDto(
        @Schema(description = "url을 얻고자 하는 사진 목록")
        @NotNull
        List<FileInfoReqDto> fileInfos
) {
}
