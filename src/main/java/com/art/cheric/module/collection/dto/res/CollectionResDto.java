package com.art.cheric.module.collection.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "컬렉션 간단 정보 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CollectionResDto {
    @Schema(description = "컬렉션 id")
    private final Long collectionId;

    @Schema(description = "컬렉션 가장 최근 작품 이미지 url")
    private final String latestArtImgUrl;

    @Schema(description = "컬렉션 이름")
    private final String name;

    @Schema(description = "컬렉션 설명")
    private final String description;

    public static CollectionResDto of(Long collectionId, String latestArtImgUrl, String name, String description) {
        return CollectionResDto.builder()
                .collectionId(collectionId)
                .latestArtImgUrl(latestArtImgUrl)
                .name(name)
                .description(description)
                .build();
    }
}
