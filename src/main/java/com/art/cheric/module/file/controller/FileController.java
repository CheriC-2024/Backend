package com.art.cheric.module.file.controller;

import com.art.cheric.global.common.DataResponseDto;
import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.file.dto.res.PresignedUrlResDto;
import com.art.cheric.module.file.service.FileService;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController implements FileControllerDocs {
    private final FileService fileService;

    @PostMapping("/presigned-url")
    public ResponseEntity<ResponseDto> getPresignedUrl(@RequestAttribute User user,
                                                       @RequestBody @Valid com.art.cheric.module.file.dto.req.PresignedUrlReqDto presignedUrl) {
        PresignedUrlResDto resDto = fileService.getPresignedUrl(user, presignedUrl);
        return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
    }
}
