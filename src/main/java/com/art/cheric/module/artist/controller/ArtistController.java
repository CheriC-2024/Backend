package com.art.cheric.module.artist.controller;

import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.artist.dto.req.ArtistReqDto;
import com.art.cheric.module.artist.dto.res.ArtistResDto;
import com.art.cheric.module.artist.service.ArtistService;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.validation.Valid;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@Slf4j
public class ArtistController implements ArtistControllerDocs {
    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ResponseDto> postArtist(@RequestAttribute("user") User user,
                                                  @RequestBody @Valid ArtistReqDto artistReq) {
        Long userId = artistService.postArtist(user, artistReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201, userId + " 사용자가 작가 인증을 요청하였습니다."));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getArtist(@RequestAttribute("user") User user,
                                                 @RequestParam(name = "artistUserId") @Nullable Long artistUserId) {
        ArtistResDto artistResDto = artistService.getArtist(user, artistUserId);
        return ResponseEntity.status(200).body(DataResponseDto.of(artistResDto, 200));
    }
}
