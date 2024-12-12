package com.art.cheric.module.following.controller;

import com.art.cheric.global.common.DataPageResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.FollowSortType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.following.service.FollowService;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.res.UserBrief2ResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
@Slf4j
public class FollowController implements FollowControllerDocs {

    private final FollowService followService;

    @PostMapping("/{followed-id}")
    public ResponseEntity<ResponseDto> postFollow(@RequestAttribute("user") User user,
                                                  @PathVariable(name = "followed-id") Long followedUserId) {
        followService.postFollow(user, followedUserId);
        return ResponseEntity.status(201).body(ResponseDto.of(201));
    }

    @DeleteMapping("/{followed-id}")
    public ResponseEntity<ResponseDto> deleteFollow(@RequestAttribute("user") User user,
                                                    @PathVariable(name = "followed-id") Long followedUserId) {
        followService.deleteFollow(user, followedUserId);
        return ResponseEntity.ok(ResponseDto.of(200));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getFollowList(
            @Schema(description = "리스트를 확인할 유저입니다.") @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "sort") FollowSortType sort,
            @RequestParam(name = "order") UserOrderType order,
            @Schema(description = "0번부터 시작합니다. 조회할 페이지 번호를 의미합니다.") @RequestParam(name = "page") int page,
            @Schema(description = "조회할 페이지 크기를 의미합니다.") @RequestParam(name = "size") int size) {

        Page<UserBrief2ResDto> resPage = followService.getFollowList(userId, sort, order, page, size);
        return ResponseEntity.status(200).body(DataPageResponseDto.of(resPage.getContent(), 200, resPage.getTotalElements(),
                resPage.getTotalPages(), resPage.getSize(), resPage.getNumberOfElements()));
    }
}
