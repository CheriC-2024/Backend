package com.art.cheric.module.exhibition.service;

import com.art.cheric.global.error.GlobalErrorCode;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.global.util.JsonResponseUtil;
import com.art.cheric.infra.chatgpt.service.ChatGptService;
import com.art.cheric.infra.cloud.service.CloudVisionService;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.repository.ArtRepository;
import com.art.cheric.module.art.error.ArtErrorCode;
import com.art.cheric.module.exhibition.dto.req.ArtChatGptReqDto;
import com.art.cheric.module.exhibition.dto.req.ArtCloudReqDto;
import com.art.cheric.module.exhibition.dto.res.ArtChatGptResDto;
import com.art.cheric.module.exhibition.dto.res.ArtCloudResDto;
import com.art.cheric.module.exhibition.error.ExhibitionErrorCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ExhibitionAiService {
    private final CloudVisionService cloudVisionService;
    private final ChatGptService chatGptService;
    private final ArtRepository artRepository;
    private final JsonResponseUtil jsonResponseUtil;

    // Google Vision API를 사용하여 이미지 색상 및 라벨 정보를 추출하는 메서드
    public List<ArtCloudResDto> postArtsProperties(ArtCloudReqDto artCloudReq) {
        // 최종 응답 데이터를 저장할 리스트 생성
        List<ArtCloudResDto> finalArtCloudRes = new ArrayList<>();

        // 요청받은 Art ID들의 유효성 검사 (중복 또는 초과 여부 확인)
        checkDuplicationAndMaxSize(artCloudReq.artIds());

        // google credentials 인증
        cloudVisionService.authenticateWithGoogleCloud();

        // Google Cloud Vision API 클라이언트 생성 및 사용
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            for (Long artId : artCloudReq.artIds()) {

                // 데이터베이스에서 Art 엔티티 조회
                Art art = getArt(artId);

                // Vision API에 요청을 보낼 요청 객체 생성
                Type fetureType = artCloudReq.cloudVisionType().getFeature();
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(
                        List.of(cloudVisionService.makeCloudVisionRequest(art.getImgUrl(), fetureType,
                                artCloudReq.cloudVisionType().getMaxResult())));

                // 현재 Art ID에 대한 응답 DTO 생성
                ArtCloudResDto artCloudRes = ArtCloudResDto.of(artId,
                        cloudVisionService.extractProperties(response, fetureType));

                // 처리된 DTO를 최종 응답 리스트에 추가
                finalArtCloudRes.add(artCloudRes);
            }
        } catch (IOException e) {
            log.error("[Cloud Vision] ImageAnnotatorClient create error : {}", e.getMessage(), e);
            throw new AppException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }

        // 최종 응답 리스트 반환
        return finalArtCloudRes;
    }


    // 요청받은 Art ID들의 중복 여부와 최대 갯수 초과 여부 확인 메서드
    private void checkDuplicationAndMaxSize(List<Long> artIds) {
        // 중복 제거를 위한 Set 생성
        Set<Long> uniqueArtIds = new HashSet<>(artIds);
        if (uniqueArtIds.size() != artIds.size()) { // 중복된 ID가 있는 경우
            throw new AppException(ExhibitionErrorCode.DUPLICATE_ART_ID);
        }
    }

    // TODO 추후 Art 관련 도메인으로 이동시키기
    private Art getArt(Long artId) {
        return artRepository.findById(artId)
                .orElseThrow(() -> new AppException(ArtErrorCode.ART_NOT_FOUND));
    }

    public ArtChatGptResDto postChatGptResult(ArtChatGptReqDto artChatGptReq) {
        // Properties 리스트를 쉼표로 구분된 문자열로 변환
        String propertiesString = artChatGptReq.artProperties().stream()
                .flatMap(art -> art.getProperties().stream())
                .distinct()
                .collect(Collectors.joining(", "));

        // Prompt 생성
        String prompt = chatGptService.buildPrompt(artChatGptReq.chatGptType(), propertiesString);

        // ChatGPT API 호출 및 응답 파싱
        String content = chatGptService.getChatGptResponse(prompt);
        JsonNode contentNode = jsonResponseUtil.parseJson(content);

        // 결과와 이유 추출
        List<String> result = jsonResponseUtil.extractStringList(contentNode, "result");
        List<String> reason = jsonResponseUtil.extractStringList(contentNode, "reason");

        return ArtChatGptResDto.of(result, reason);
    }

}
