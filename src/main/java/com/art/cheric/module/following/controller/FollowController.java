package com.art.cheric.module.following.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.following.service.FollowService;
import com.art.cheric.module.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follow")
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
        return ResponseEntity.status(200).body(ResponseDto.of(200, "Ok"));
    }
}
