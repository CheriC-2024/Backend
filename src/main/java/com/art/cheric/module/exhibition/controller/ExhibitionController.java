package com.art.cheric.module.exhibition.controller;

import com.art.cheric.global.common.DataPageResponseDto;
import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.ExhibitionOrderType;
import com.art.cheric.module.exhibition.dto.req.ExhibitionReqDto;
import com.art.cheric.module.exhibition.dto.req.ExhibitionReviewReqDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionListResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionReviewDetailResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionReviewListResDto;
import com.art.cheric.module.exhibition.service.ExhibitionService;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exhibitions")
@RequiredArgsConstructor
@Slf4j
public class ExhibitionController implements ExhibitionControllerDocs {
    private final ExhibitionService exhibitionService;

    @PostMapping
    public ResponseEntity<ResponseDto> postExhibition(@RequestAttribute("user") User user,
                                                      @RequestBody @Valid ExhibitionReqDto exhibitionReq) {
        Long exhibitionId = exhibitionService.postExhibition(user, exhibitionReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201, exhibitionId + " 전시가 생성되었습니다."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getExhibitionContent(@PathVariable(name = "id") Long exhibitionId) {
        ExhibitionResDto resDto = exhibitionService.getExhibitionContent(exhibitionId);
        return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
    }

    @PostMapping("/{id}/heart")
    public ResponseEntity<ResponseDto> postHeart(@RequestAttribute("user") User user,
                                                 @PathVariable(name = "id") Long exhibitionId) {
        int heartCount = exhibitionService.postHeart(user, exhibitionId);
        return ResponseEntity.status(201).body(DataResponseDto.of(heartCount, 201));
    }

    @DeleteMapping("/{id}/heart")
    public ResponseEntity<ResponseDto> deleteHeart(@RequestAttribute("user") User user,
                                                   @PathVariable(name = "id") Long exhibitionId) {
        int heartCount = exhibitionService.deleteHeart(user, exhibitionId);
        return ResponseEntity.status(200).body(DataResponseDto.of(heartCount, 200));
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<ResponseDto> postReview(@RequestAttribute("user") User user,
                                                  @PathVariable(name = "id") Long exhibitionId,
                                                  @RequestBody @Valid ExhibitionReviewReqDto exhibitionReviewReq) {
        exhibitionService.postExhibitionReview(user, exhibitionId, exhibitionReviewReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201));
    }

    @PostMapping("/{id}/reviews/{reviewId}")
    public ResponseEntity<ResponseDto> postReview(@RequestAttribute("user") User user,
                                                  @PathVariable(name = "id") Long exhibitionId,
                                                  @PathVariable(name = "reviewId") Long reviewId,
                                                  @RequestBody @Valid ExhibitionReviewReqDto exhibitionReviewReq) {
        exhibitionService.postExhibitionReReview(user, exhibitionId, reviewId, exhibitionReviewReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201));
    }

    @PostMapping("/{id}/reviews/{reviewId}/heart")
    public ResponseEntity<ResponseDto> postReviewHeart(@RequestAttribute("user") User user,
                                                       @PathVariable(name = "id") Long exhibitionId,
                                                       @PathVariable(name = "reviewId") Long reviewId) {
        int heartCount = exhibitionService.postReviewHeart(user, exhibitionId, reviewId);
        return ResponseEntity.status(201).body(DataResponseDto.of(heartCount, 201));
    }

    @DeleteMapping("/{id}/reviews/{reviewId}/heart")
    public ResponseEntity<ResponseDto> deleteReviewHeart(@RequestAttribute("user") User user,
                                                         @PathVariable(name = "id") Long exhibitionId,
                                                         @PathVariable(name = "reviewId") Long reviewId) {
        int heartCount = exhibitionService.deleteReviewHeart(user, exhibitionId, reviewId);
        return ResponseEntity.status(200).body(DataResponseDto.of(heartCount, 200));
    }

    @PostMapping("/{id}/hits")
    public ResponseEntity<ResponseDto> postHits(@RequestAttribute("user") User user,
                                                @PathVariable(name = "id") Long exhibitionId) {
        int hitCount = exhibitionService.postHits(user, exhibitionId);
        return ResponseEntity.status(201).body(DataResponseDto.of(hitCount, 201));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getExhibitions(
            @Schema(name = "작품 id", description = "해당 작품을 포함한 전시 리스트를 불러옵니다.") @RequestParam(name = "artId") @Nullable Long artId,
            @Schema(name = "사용자 id", description = "해당 사용자의 전시 리스트를 불러옵니다.") @RequestParam(name = "userId") @Nullable Long userId,
            @Schema(name = "정렬 기준", description = "정렬 기준에 맞춰 불러옵니다.") @RequestParam(name = "order") ExhibitionOrderType order,
            @Schema(name = "페이지 번호", description = "0번부터 시작하는 페이지 번호에 따라 불러옵니다.") @RequestParam(name = "page") int page,
            @Schema(name = "페이지 내 아이템 개수", description = "해당하는 페이지에서 아이템 개수만큼 불러옵니다.") @RequestParam(name = "size") int size) {
        Page<ExhibitionListResDto> resPage = exhibitionService.getExhibitions(artId, userId, order, page, size);
        return ResponseEntity.status(200).body(
                DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                        resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<ResponseDto> getExhibitionReviews(@PathVariable(name = "id") Long exhibitionId,
                                                            @RequestParam(name = "page") int page,
                                                            @RequestParam(name = "size") int size) {
        Page<ExhibitionReviewListResDto> resPage = exhibitionService.getExhibitionReviews(exhibitionId, page, size);
        return ResponseEntity.status(200).body(
                DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                        resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }

    @GetMapping("/{id}/reviews/{reviewId}")
    public ResponseEntity<ResponseDto> getExhibitionReviews(@PathVariable(name = "id") Long exhibitionId,
                                                            @PathVariable(name = "reviewId") Long reviewId) {
        ExhibitionReviewDetailResDto resDto = exhibitionService.getExhibitionReviewsById(exhibitionId, reviewId);
        return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
    }
}
