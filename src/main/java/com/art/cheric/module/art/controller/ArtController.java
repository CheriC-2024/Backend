package com.art.cheric.module.art.controller;

import com.art.cheric.global.common.DataPageResponseDto;
import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.ArtOrderType;
import com.art.cheric.global.enums.ArtType;
import com.art.cheric.module.art.dto.req.ArtReqDto;
import com.art.cheric.module.art.dto.req.OwnArtReqDto;
import com.art.cheric.module.art.dto.res.ArtBriefListResDto;
import com.art.cheric.module.art.dto.res.ArtDescriptionResDto;
import com.art.cheric.module.art.dto.res.ArtResDto;
import com.art.cheric.module.art.dto.res.ArtTypeSortListResDto;
import com.art.cheric.module.art.service.ArtService;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;

@RestController
@RequestMapping("/api/arts")
@RequiredArgsConstructor
@Slf4j
public class ArtController implements ArtControllerDocs {
    private final ArtService artService;

    @PostMapping("/own")
    public ResponseEntity<ResponseDto> postOwnArt(@RequestAttribute("user") User user,
                                                  @RequestBody @Valid OwnArtReqDto ownArtReq) {
        Long artId = artService.postOwnArt(user, ownArtReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201, artId + " 작품이 생성되었습니다."));
    }

    @PostMapping("/artist")
    public ResponseEntity<ResponseDto> postArtistArt(@RequestAttribute("user") User user,
                                                     @RequestBody @Valid ArtReqDto artReq) {
        Long artId = artService.postArtistArt(user, artReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201, artId + " 작품이 생성되었습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getArt(@RequestAttribute("user") User user,
                                              @PathVariable("id") Long artId) {
        ArtResDto resDto = artService.getArt(artId);
        return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
    }

    @PostMapping("/{id}/heart")
    public ResponseEntity<ResponseDto> postHeart(@RequestAttribute("user") User user,
                                                 @PathVariable("id") Long artId) {
        int heartCount = artService.postHeart(user, artId);
        return ResponseEntity.ok(DataResponseDto.of(heartCount, 200));
    }

    @DeleteMapping("/{id}/heart")
    public ResponseEntity<ResponseDto> deleteHeart(@RequestAttribute("user") User user,
                                                   @PathVariable("id") Long artId) {
        int heartCount = artService.deleteHeart(user, artId);
        return ResponseEntity.ok(DataResponseDto.of(heartCount, 200));
    }

    @GetMapping("/owns/{id}")
    public ResponseEntity<ResponseDto> getOwnArtDescription(@RequestAttribute("user") User user,
                                                            @PathVariable(name = "id") Long artId) {
        ArtDescriptionResDto resDto = artService.getOwnArtDescription(user, artId);
        return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getArts(
            @RequestAttribute("user") User user,
            @Schema(description = "ture라면, following 하는 사용자의 작품만, false라면, following 안 하는 사용자의 작품만, " +
                    "null이라면 following 과 관계없이 조회합니다.") @RequestParam(name = "isFollowing") @Nullable Boolean isFollowing,
            @Schema(description = "사용자 id에 맞는 작품을 반환합니다.") @RequestParam(name = "userId") @Nullable Long userId,
            @Schema(description = "true라면, 소장 작품만 / false 라면 작가 작품만 / null이라면 모두 반환합니다.") @RequestParam(name = "isCollectorsArt") @Nullable Boolean isCollectorsArt,
            @Schema(description = "미술 분야에 맞는 작품만 반환합니다.") @RequestParam(name = "artType") @Nullable ArtType artType,
            @RequestParam(name = "order") ArtOrderType order,
            @Schema(description = "0번부터 시작합니다. 조회할 페이지 번호를 의미합니다.") @RequestParam(name = "page") int page,
            @Schema(description = "조회할 페이지 크기를 의미합니다.") @RequestParam(name = "size") int size) {
        Page<ArtBriefListResDto> resPage = artService.getArts(user, isFollowing, userId, isCollectorsArt, artType, order, page, size);
        return ResponseEntity.status(200).body(DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }


    @GetMapping("/art-type")
    public ResponseEntity<ResponseDto> getArtsGroupByArtType(@RequestAttribute("user") User user,
                                                             @RequestParam(name = "order") ArtOrderType order,
                                                             @Schema(description = "0번부터 시작합니다. 조회할 분야 별 작품의 페이지 번호를 의미합니다.") @RequestParam(name = "page") int page,
                                                             @Schema(description = "조회할 분야 별 작품의 페이지 크기를 의미합니다.") @RequestParam(name = "size") int size) {
        Page<ArtTypeSortListResDto> resPage = artService.getArtsGroupByArtType(user, order, page, size);
        return ResponseEntity.status(200).body(DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }

}
