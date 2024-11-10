package com.art.cheric.module.file.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PresignedUrlResDto {
    @Schema(description = "S3에 접근할 수 있는 url 모음")
    private final List<FileInfoResDto> urls;

    public static PresignedUrlResDto from(List<FileInfoResDto> urls) {
        return PresignedUrlResDto.builder()
                .urls(urls)
                .build();
    }
}
