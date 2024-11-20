package com.art.cheric.module.art.controller;

import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.art.dto.req.ArtReqDto;
import com.art.cheric.module.art.dto.req.OwnArtReqDto;
import com.art.cheric.module.art.dto.res.ArtResDto;
import com.art.cheric.module.art.service.ArtService;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/arts")
@RequiredArgsConstructor
@Slf4j
public class ArtController implements ArtControllerDocs {
    private final ArtService artService;

    @PostMapping("/own")
    public ResponseEntity<ResponseDto> postOwnArt(@RequestAttribute("user") User user, @RequestBody @Valid OwnArtReqDto ownArtReq) {
        artService.postOwnArt(user, ownArtReq);
        return ResponseEntity.status(201).body(ResponseDto.of( 201));
    }

    @PostMapping("/artist")
    public ResponseEntity<ResponseDto> postArtistArt(@RequestAttribute("user") User user, @RequestBody @Valid  ArtReqDto artReq) {
        artService.postArtistArt(user, artReq);
        return ResponseEntity.status(201).body(ResponseDto.of( 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getArt(@RequestAttribute("user") User user, @PathVariable("id") Long artId) {
        ArtResDto resDto = artService.getArt(user, artId);
        return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
    }

}
