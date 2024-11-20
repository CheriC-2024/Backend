package com.art.cheric.module.exhibition.controller;

import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.exhibition.dto.req.ArtChatGptReqDto;
import com.art.cheric.module.exhibition.dto.req.ArtCloudReqDto;
import com.art.cheric.module.exhibition.dto.res.ArtChatGptResDto;
import com.art.cheric.module.exhibition.dto.res.ArtCloudResDto;
import com.art.cheric.module.exhibition.service.ExhibitionAiService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exhibition/ai")
@RequiredArgsConstructor
@Slf4j
public class ExhibitionAiController implements ExhibitionAiControllerDocs {
    private final ExhibitionAiService exhibitionAiService;

    @PostMapping("/cloud")
    public ResponseEntity<ResponseDto> postArtsProperties(@RequestBody @Valid ArtCloudReqDto artCloudReq) {
        List<ArtCloudResDto> resDto = exhibitionAiService.postArtsProperties(artCloudReq);
        return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
    }

    @PostMapping("/chat-gpt")
    public ResponseEntity<ResponseDto> postChatGptResult(@RequestBody @Valid ArtChatGptReqDto artChatGptReq) {
        ArtChatGptResDto resDto = exhibitionAiService.postChatGptResult(artChatGptReq);
        return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
    }

}
