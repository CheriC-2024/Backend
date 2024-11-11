package com.art.cheric.module.file.dto.req;

import com.art.cheric.global.enums.FilePathType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record FileInfoReqDto(
        @Schema(description = "저장할 폴더 경로", example = "USER_IMG")
        @NotNull
        FilePathType path,

        @Schema(description = "파일 이름", example = "user-profile.png")
        @NotNull
        String name,


        @Schema(description = "업로드할 파일 사이즈 크기", example = "1048576")
        @NotNull
        long size
) {
}
