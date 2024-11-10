package com.art.cheric.module.file.service;

import com.art.cheric.global.util.FileUtil;
import com.art.cheric.global.util.S3Util;
import com.art.cheric.module.file.dto.req.FileInfoReqDto;
import com.art.cheric.module.file.dto.res.FileInfoResDto;
import com.art.cheric.module.file.dto.res.PresignedUrlResDto;
import com.art.cheric.module.user.domain.entity.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final S3Util s3Util;
    private final FileUtil fileUtil;

    public PresignedUrlResDto getPresignedUrl(User user, com.art.cheric.module.file.dto.req.PresignedUrlReqDto presignedUrl) {
        List<FileInfoResDto> fileUploadInfos = new ArrayList<>();

        for (FileInfoReqDto fileInfo : presignedUrl.fileInfos()) {
            // 파일 유효성 검사
            fileUtil.checkFileInfoValidation(fileInfo);

            // 저장할 키 만들기
            String key = fileInfo.path().name() + "/" + user.getId() + "/" + UUID.randomUUID() + "-" + fileInfo.name();

            // preSignedUrl 추출 및 리스트에 추가
            URL preSignedUrl = s3Util.generatePutPresignedUrl(key);
            fileUploadInfos.add(FileInfoResDto.from(preSignedUrl));
        }

        return PresignedUrlResDto.from(fileUploadInfos);
    }
}
