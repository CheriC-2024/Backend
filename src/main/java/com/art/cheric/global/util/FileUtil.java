package com.art.cheric.global.util;

import static com.art.cheric.module.file.error.FileErrorCode.FILE_NAME_LENGTH_ERROR;
import static com.art.cheric.module.file.error.FileErrorCode.FILE_SIZE_ERROR;
import static com.art.cheric.module.file.error.FileErrorCode.INVALID_EXTENSION;
import static com.art.cheric.module.file.error.FileErrorCode.INVALID_FILE_NAME;
import static com.art.cheric.module.file.error.FileErrorCode.NEED_EXTENSION;

import com.art.cheric.global.enums.ValidateFileType;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.file.dto.req.FileInfoReqDto;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileUtil {

    public static final long FILE_MAX_SIZE = 5 * 1024 * 1024L;
    private final Pattern VALID_FILENAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");

    public void checkFileInfoValidation(FileInfoReqDto fileInfo) {
        // 파일 이름 평식 및 이름 추출
        checkExtensionExist(fileInfo.name());
        String name = getFileName(fileInfo.name());

        // 파일 사이즈 유효성 검사
        checkFileSizeIsValid(fileInfo.size());

        // 파일 이름 유효성 검사
        checkFileNameLengthIsValid(name);
        checkFileNameIsSafe(name);

        // 파일 확장자 유효성 검사
        checkFileExtensionIsValid(fileInfo.path().getFileType(), getFileExtension(fileInfo.name()));
    }

    // 파일 사이즈 검사
    private void checkFileSizeIsValid(long fileSize) {
        if (fileSize > FILE_MAX_SIZE) {
            throw new AppException(FILE_SIZE_ERROR);
        }
    }

    // 파일 이름 길이 제한 검사
    private void checkFileNameLengthIsValid(String name) {
        if (name.length() >= 100) {
            throw new AppException(FILE_NAME_LENGTH_ERROR);
        }
    }

    // 파일 이름이 공격 위험성이 없는지 검사
    private void checkFileNameIsSafe(String name) {
        if (!VALID_FILENAME_PATTERN.matcher(name).matches()) {
            throw new AppException(INVALID_FILE_NAME);
        }
    }

    // 파일 확장자 유효성 검사
    private void checkFileExtensionIsValid(ValidateFileType validateFileType, String extension) {
        if (!validateFileType.getExtensions().contains(extension)) {
            throw new AppException(INVALID_EXTENSION);
        }
    }

    // 파일 이름 추출
    private String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    // 파일 확장자 추출
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // 파일 확장자 존재 여부 확인
    private void checkExtensionExist(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            throw new AppException(NEED_EXTENSION);
        }
    }
}
