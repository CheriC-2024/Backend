package com.art.cheric.module.collection.dto.res;

import com.art.cheric.module.art.dto.res.ArtBriefResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "컬렉션 상세 응답 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class CollectionArtResDto {
    @Schema(description = "컬렉션 id")
    private final Long collectionId;

    @Schema(description = "컬렉션 이름")
    private final String name;

    @Schema(description = "컬렉션 작품 리스트")
    List<ArtBriefResDto> artBriefRess;


    public static CollectionArtResDto of(Long collectionId, String name, List<ArtBriefResDto> artBriefRess) {
        return CollectionArtResDto.builder()
                .collectionId(collectionId)
                .name(name)
                .artBriefRess(artBriefRess)
                .build();
    }
}
