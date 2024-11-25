package com.art.cheric.module.art.service;

import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.entity.ArtFile;
import com.art.cheric.module.art.domain.entity.ArtHeart;
import com.art.cheric.module.art.domain.entity.ArtPart;
import com.art.cheric.module.art.domain.entity.ArtPlusImage;
import com.art.cheric.module.art.domain.entity.ArtistArt;
import com.art.cheric.module.art.domain.entity.OwnArt;
import com.art.cheric.module.art.domain.repository.ArtFileRepository;
import com.art.cheric.module.art.domain.repository.ArtHeartRepository;
import com.art.cheric.module.art.domain.repository.ArtPartRepository;
import com.art.cheric.module.art.domain.repository.ArtPlusImageRepository;
import com.art.cheric.module.art.domain.repository.ArtRepository;
import com.art.cheric.module.art.domain.repository.ArtistArtRepository;
import com.art.cheric.module.art.domain.repository.OwnArtRepository;
import com.art.cheric.module.art.dto.req.ArtReqDto;
import com.art.cheric.module.art.dto.req.OwnArtReqDto;
import com.art.cheric.module.art.dto.res.ArtDescriptionResDto;
import com.art.cheric.module.art.dto.res.ArtResDto;
import com.art.cheric.module.art.dto.res.OwnArtResDto;
import com.art.cheric.module.art.error.ArtErrorCode;
import com.art.cheric.module.artist.service.ArtistService;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.res.UserResDto;
import com.art.cheric.module.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArtService {
    private final ArtRepository artRepository;
    private final ArtistArtRepository artistArtRepository;
    private final ArtPartRepository artPartRepository;
    private final ArtPlusImageRepository artPlusImageRepository;
    private final ArtFileRepository artFileRepository;
    private final OwnArtRepository ownArtRepository;
    private final ArtHeartRepository artHeartRepository;
    private final ArtistService artistService;
    private final UserService userService;

    // 소장 작품 생성
    @Transactional
    public Long postOwnArt(User user, OwnArtReqDto ownArtReq) {
        // art 생성
        ArtReqDto artReq = ownArtReq.artBasicInfo();
        Art art = artRepository.save(
                Art.of(artReq.name(), artReq.description(), artReq.series(), artReq.material(), artReq.madeAt(), null,
                        artReq.horizontalSize(), artReq.verticalSize(), artReq.imgUrl(), true));

        // artPart 생성
        for (ArtType artType : artReq.artTypes()) {
            artPartRepository.save(ArtPart.of(art, artType));
        }

        // ownArt PlusImage 생성
        for (String imgUrl : ownArtReq.imgUrl()) {
            artPlusImageRepository.save(ArtPlusImage.of(art, imgUrl));
        }

        // ownArt 생성
        OwnArt ownArt = ownArtRepository.save(
                OwnArt.of(art, user, ownArtReq.artistName(), ownArtReq.price(), ownArtReq.isPriceOpen()));

        // ownArt File 생성
        for (String fileUrl : ownArtReq.fileUrl()) {
            artFileRepository.save(ArtFile.of(ownArt, fileUrl));
        }

        return art.getId();
    }

    // 작가 작품 생성
    @Transactional
    public Long postArtistArt(User user, ArtReqDto artReq) {
        // 체리 가격 검증
        checkCherryCountValidate(artReq);

        // 작가 유효 상태 검증
        artistService.checkThisUserIsValidArtist(user.getId());

        // art 생성
        Art art = artRepository.save(
                Art.of(artReq.name(), artReq.description(), artReq.series(), artReq.material(), artReq.madeAt(),
                        artReq.cherryPrice(),
                        artReq.horizontalSize(), artReq.verticalSize(), artReq.imgUrl(), false));

        // artPart 생성
        for (ArtType artType : artReq.artTypes()) {
            artPartRepository.save(ArtPart.of(art, artType));
        }

        // artistArt 생성
        artistArtRepository.save(ArtistArt.of(art, user));

        return art.getId();
    }

    private void checkCherryCountValidate(ArtReqDto artReq) {
        if (artReq.cherryPrice() == null || artReq.cherryPrice() < 0) {
            throw new AppException(ArtErrorCode.INVALID_CHERRY_PRICE);
        }
    }

    // 작품 상세 확인
    public ArtResDto getArt(Long artId) {
        // 있는 작품인지 확인
        Art art = findArtByIdWithValidation(artId);

        // 기본 데이터 준비
        String artistName;
        Long artPrice = null;
        UserResDto userResDto;

        // 작품 종류에 따른 연결
        if (art.isCollectorsArt()) {
            OwnArt ownArt = findOwnArtByArtId(art.getId());
            artistName = ownArt.getArtistName();
            artPrice = ownArt.getPrice();
            userResDto = userService.createUserResDto(ownArt.getUser());
        } else {
            ArtistArt artistArt = findArtistArtByArtId(art.getId());
            artistName = artistArt.getUser().getName();
            userResDto = userService.createUserResDto(artistArt.getUser());
        }

        // 값 제공
        return ArtResDto.of(
                art.isCollectorsArt(),
                art.getImgUrl(),
                art.getCherryPrice(),
                art.getName(),
                artistName,
                art.getSeries(),
                art.getHorizontalSize(),
                art.getVerticalSize(),
                art.getMaterial(),
                art.getMadeAt(),
                getArtTypes(art.getArtParts()),
                userResDto,
                art.getHeartCount(),
                art.getDescription(),
                OwnArtResDto.from(artPrice)
        );
    }


    @Transactional
    public int postHeart(User user, Long artId) {
        // 유효한 작품인지 확인
        Art art = findArtByIdWithValidation(artId);

        // 이미 하트 설정되어 있는지
        checkArtHeartUnique(user.getId(), artId);

        // 하트 추가
        artHeartRepository.save(ArtHeart.of(user, art));

        // 하트 카운트 추가
        art.plusHeartCount();

        // 작품 변경 정보 저장
        artRepository.save(art);

        // TODO 해당 작품 작가 & 소장자에게 알림 날리기

        return art.getHeartCount();
    }

    private void checkArtHeartUnique(Long userId, Long artId) {
        if (artHeartRepository.findByArtIdAndUserId(artId, userId).isPresent()) {
            throw new AppException(ArtErrorCode.ART_HEART_ALREADY_EXIST);
        }
    }

    @Transactional
    public int deleteHeart(User user, Long artId) {
        // 유효한 작품인지 확인
        Art art = findArtByIdWithValidation(artId);

        // 하트한 적이 있는지 확인
        ArtHeart artHeart = artHeartRepository.findByArtIdAndUserId(artId, user.getId())
                .orElseThrow(() -> new AppException(ArtErrorCode.ART_HEART_DOESNT_EXIST));

        // 하트 삭제
        artHeartRepository.delete(artHeart);

        // 하트 카운트 감소
        art.minusHeartCount();

        // 작품 변경 정보 저장
        artRepository.save(art);

        return art.getHeartCount();
    }

    // 소장 작품 소개 불러오기
    public ArtDescriptionResDto getOwnArtDescription(User user, Long artId) {
        // 작품 찾기
        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new AppException(ArtErrorCode.ART_NOT_FOUND));

        // 작품이 작가 작품인지 확인
        if (!art.isCollectorsArt()) {
            throw new AppException(ArtErrorCode.IS_NOT_OWN_ART);
        }

        // 자신의 작품인지 확인
        OwnArt ownArt = ownArtRepository.findByUserIdAndArtId(user.getId(), art.getId())
                .orElseThrow(() -> new AppException(ArtErrorCode.YOUR_OWN_ART_NOT_FOUND));

        // 작품이 유효한 상태인지 확인
        if (ownArt.isNotValidState()) {
            throw new AppException(ArtErrorCode.OWN_ART_INVALID);
        }

        return ArtDescriptionResDto.of(artId, art.getDescription());
    }

    // ArtPart > ArtType 변경
    public List<ArtType> getArtTypes(List<ArtPart> artParts) {
        return artParts.stream()
                .map(ArtPart::getArtType)
                .collect(Collectors.toList());
    }

    // 작품 조회 및 유효성 검증
    public Art findArtByIdWithValidation(Long artId) {
        // 작품 찾기
        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new AppException(ArtErrorCode.ART_NOT_FOUND));

        // 작품 유효성 검증
        checkArtIsValid(art);

        // 작품 반환
        return art;
    }

    // 작품 사용 가능 여부 확인 > 작가: 탈퇴 시 / 소장: 인증 전
    private void checkArtIsValid(Art art) {
        if (!art.isCollectorsArt()) {
            checkArtistArtValid(art.getId());
        } else {
            checkOwnArtValid(art.getId());
        }
    }

    // 소장 작품 유효성 확인
    private void checkOwnArtValid(Long artId) {
        OwnArt ownArt = findOwnArtByArtId(artId);
        if (ownArt.isNotValidState()) {
            throw new AppException(ArtErrorCode.OWN_ART_INVALID);
        }
    }

    // 소장 작품 존재 여부 확인
    public OwnArt findOwnArtByArtId(Long artId) {
        return ownArtRepository.findByArtId(artId).orElseThrow(
                () -> new AppException(ArtErrorCode.OWN_ART_NOT_FOUND)
        );
    }

    // 작가 작품 유효성 확인
    public void checkArtistArtValid(Long artId) {
        ArtistArt artistArt = findArtistArtByArtId(artId);
        if (!artistArt.isUsable()) {
            throw new AppException(ArtErrorCode.ARTIST_ART_INVALID);
        }
    }

    // 작가 작품 유효성 확인
    public ArtistArt findArtistArtByArtId(Long artId) {
        return artistArtRepository.findByArtId(artId).orElseThrow(
                () -> new AppException(ArtErrorCode.ARTIST_ART_NOT_FOUND)
        );
    }
}
