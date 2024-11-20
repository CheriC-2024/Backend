package com.art.cheric.module.exhibition.dto.req;

import com.art.cheric.global.enums.ChatGptType;
import com.art.cheric.module.exhibition.dto.res.ArtCloudResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Schema(description = "Chat Gpt 요청 DTO")
public record ArtChatGptReqDto(
        @Schema(description = "요청하는 Chat Gpt 종류", example = "THEME")
        @NotNull
        @Enumerated(EnumType.STRING)
        ChatGptType chatGptType,

        @Schema(description = "요청하는 작품에서 추출된 속성", example = "[{1, [\"Blue\",\"Summer\",\"Cool\"]}]")
        @NotEmpty
        @Size(max = 30, message = "작품은 최대 30개까지만 설정할 수 있습니다.")
        List<ArtCloudResDto> artProperties
) {
}
