package com.art.cheric.infra.cloud.service;

import com.art.cheric.global.error.GlobalErrorCode;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.exhibition.error.ExhibitionErrorCode;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudVisionService {

    private static final String GOOGLE_CREDENTIAL_FILE = "google-credentials.json";


    public final int EXTRACT_COLOR_MAX_SIZE = 4;
    public final int EXTRACT_LABEL_MAX_SIZE = 5;

    public void authenticateWithGoogleCloud() {
        try {
            // 클래스패스에서 JSON 파일 읽기
            InputStream credentialsStream = new ClassPathResource(GOOGLE_CREDENTIAL_FILE).getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

            // Vision API 설정에 자격 증명 추가
            ImageAnnotatorSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            log.info("[Cloud Vision] Authentication successful!");
        } catch (IOException e) {
            log.error("[Cloud Vision] google credential error : {}", e.getMessage(), e);
            throw new AppException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // Type에 따라 속성 추출
    public List<String> extractProperties(BatchAnnotateImagesResponse response, Type fetureType) {
        List<String> properties = new ArrayList<>();
        for (AnnotateImageResponse annotateImageRes : response.getResponsesList()) {
            if (annotateImageRes.hasError()) {
                log.error("[Cloud Vision] annotationImageRes has error : {}", annotateImageRes.getError().getMessage());
                continue;
            }

            if (fetureType == Type.IMAGE_PROPERTIES) {
                properties.addAll(getImageColors(annotateImageRes));
            } else if (fetureType == Type.LABEL_DETECTION) {
                properties.addAll(getImageLabels(annotateImageRes));
            }
        }
        return properties;
    }

    // 이미지 색상 추출
    private List<String> getImageColors(AnnotateImageResponse annotateImage) {
        return annotateImage.getImagePropertiesAnnotation()
                .getDominantColors()
                .getColorsList()
                .stream()
                .sorted((c1, c2) -> Float.compare(c2.getScore(), c1.getScore()))
                .limit(EXTRACT_COLOR_MAX_SIZE)
                .map(colorInfo -> rgbToHex(colorInfo.getColor().getRed(), colorInfo.getColor().getGreen(),
                        colorInfo.getColor().getBlue()))
                .toList();
    }

    // 이미지 라벨 추출
    private List<String> getImageLabels(AnnotateImageResponse annotateImage) {
        return annotateImage.getLabelAnnotationsList().stream()
                .map(EntityAnnotation::getDescription)
                .limit(EXTRACT_LABEL_MAX_SIZE)
                .toList();
    }


    // vision api 요청 객체 생성
    public AnnotateImageRequest makeCloudVisionRequest(String filePath, Feature.Type requestMethod, int maxResult) {
        // 이미지 속성 탐지를 위한 feature 객체 생성
        Feature feat = Feature.newBuilder()
                .setType(requestMethod)
                .setMaxResults(maxResult)
                .build();

        // 요청 이미지 객체 생성
        Image img = getCloudVisionImage(filePath);

        // 이미지와 기능 포함하는 요청 객체 생성
        return AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
    }

    // filePath로  요청 Image 객체 생성
    // URL로 요청 Image 객체 생성
    private Image getCloudVisionImage(String fileUrl) {
        // URL에서 데이터 읽기
        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            ByteString imgBytes = ByteString.readFrom(inputStream);

            return Image.newBuilder()
                    .setContent(imgBytes)
                    .build();
        } catch (MalformedURLException e) {
            log.error("[Cloud Vision] Invalid URL {} : {}", fileUrl, e.getMessage());
            throw new AppException(ExhibitionErrorCode.INVALID_URL_PATH);
        } catch (IOException e) {
            log.error("[Cloud Vision] Failed to fetch image from URL {} : {}", fileUrl, e.getMessage());
            throw new AppException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // rgb hex 값으로 변환
    private String rgbToHex(float red, float green, float blue) {
        return String.format("#%02x%02x%02x", Math.round(red), Math.round(green), Math.round(blue));
    }
}
