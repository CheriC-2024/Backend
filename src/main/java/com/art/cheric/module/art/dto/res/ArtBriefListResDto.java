package com.art.cheric.module.art.dto.res;

import com.art.cheric.module.user.dto.res.UserBriefResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작품 정보 리스트 간단 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtBriefListResDto {

    @Schema(description = "작품 id", example = "1")
    private final Long artId;

    @Schema(description = "작품 이미지 경로", example = "https://cheric-bucket.s3.ap-northeast-2.amazonaws.com/OWN_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png")
    private final String imgUrl;

    @Schema(description = "작품 이름", example = "별이 빛나는 밤에")
    private final String name;

    @Schema(description = "작품 체리 개수", example = "null")
    private final Integer cherryNum;

    @Schema(description = "작품 생성 일시", example = "2024.08.27 22:22")
    private final String createdAt;

    @Schema(description = "작품 소유주 정보")
    private final UserBriefResDto userRes;

    public static ArtBriefListResDto of(Long artId, String imgUrl, String name, Integer cherryNum, String createdAt,
                                        UserBriefResDto userRes) {
        return ArtBriefListResDto.builder()
                .artId(artId)
                .imgUrl(imgUrl)
                .name(name)
                .cherryNum(cherryNum)
                .createdAt(createdAt)
                .userRes(userRes)
                .build();
    }
}
