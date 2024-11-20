package com.art.cheric.module.art.service;

import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.entity.ArtFile;
import com.art.cheric.module.art.domain.entity.ArtPart;
import com.art.cheric.module.art.domain.entity.ArtPlusImage;
import com.art.cheric.module.art.domain.entity.ArtistArt;
import com.art.cheric.module.art.domain.entity.OwnArt;
import com.art.cheric.module.art.domain.repository.ArtFileRepository;
import com.art.cheric.module.art.domain.repository.ArtPartRepository;
import com.art.cheric.module.art.domain.repository.ArtPlusImageRepository;
import com.art.cheric.module.art.domain.repository.ArtRepository;
import com.art.cheric.module.art.domain.repository.ArtistArtRepository;
import com.art.cheric.module.art.domain.repository.OwnArtRepository;
import com.art.cheric.module.art.dto.req.ArtReqDto;
import com.art.cheric.module.art.dto.req.OwnArtReqDto;
import com.art.cheric.module.art.dto.res.ArtResDto;
import com.art.cheric.module.art.dto.res.OwnArtResDto;
import com.art.cheric.module.art.error.ArtErrorCode;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.dto.res.UserResDto;
import java.util.List;
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


    // 소장 작품 생성
    @Transactional
    public void postOwnArt(User user, OwnArtReqDto ownArtReq) {
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
    }

    // 작가 작품 생성
    @Transactional
    public void postArtistArt(User user, ArtReqDto artReq) {
        // 체리 가격 검증
        checkCherryCountValidate(artReq);

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
    }

    private void checkCherryCountValidate(ArtReqDto artReq) {
        if (artReq.cherryPrice() == null || artReq.cherryPrice() < 0) {
            throw new AppException(ArtErrorCode.INVALID_CHERRY_PRICE);
        }
    }

    // 작품 상세 확인
    public ArtResDto getArt(User user, Long artId) {
        // 있는 작품인지 확인
        Art art = artRepository.findById(artId)
                .orElseThrow(() -> new AppException(ArtErrorCode.ART_NOT_FOUND));

        // 기본 데이터 준비
        OwnArt ownArt = null;
        ArtistArt artistArt = null;
        String artistName = user.getName();

        // 작품 종류에 따른 연결
        if (art.isCollectorsArt()) {
            ownArt = ownArtRepository.findByUserIdAndArtId(user.getId(), art.getId())
                    .orElseThrow(() -> new AppException(ArtErrorCode.OWN_ART_NOT_FOUND));
            artistName = ownArt.getArtistName();
        } else {
            artistArt = artistArtRepository.findByUserIdAndArtId(user.getId(), art.getId())
                    .orElseThrow(() -> new AppException(ArtErrorCode.ARTIST_ART_NOT_FOUND));
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
                List.of(art.getArtParts().get(0).getArtType(), art.getArtParts().get(1).getArtType()),
                UserResDto.of(
                        user.getName(),
                        user.getInfo(),
                        List.of(user.getUserParts().get(0).getUserArtType(),
                                user.getUserParts().get(1).getUserArtType())
                ),
                art.getHeartCount(),
                art.getDescription(),
                ownArt != null && ownArt.isPriceOpen() ? OwnArtResDto.from(
                        ownArt.getPrice()
                ) : null,
                artistArt != null ? artistArt.getId() : null
        );
    }
}
