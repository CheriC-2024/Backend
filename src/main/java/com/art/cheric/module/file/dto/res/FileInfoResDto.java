package com.art.cheric.module.file.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URL;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FileInfoResDto {
    @Schema(description = "S3에 접근할 수 있는 url, 해당 경로로 사진을 담은 PUT 요청을 하는 것")
    private final URL presignedUrl;

    public static FileInfoResDto from(URL presignedUrl) {
        return FileInfoResDto.builder()
                .presignedUrl(presignedUrl)
                .build();
    }
}
