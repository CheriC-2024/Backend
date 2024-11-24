package com.art.cheric.module.artist.service;


import com.art.cheric.global.enums.ValidateState;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.artist.domain.entity.Artist;
import com.art.cheric.module.artist.domain.entity.ArtistContact;
import com.art.cheric.module.artist.domain.entity.ArtistFile;
import com.art.cheric.module.artist.domain.repository.ArtistFileRepository;
import com.art.cheric.module.artist.domain.repository.ArtistRepository;
import com.art.cheric.module.artist.dto.req.ArtistBasicReqDto;
import com.art.cheric.module.artist.dto.req.ArtistContactReqDto;
import com.art.cheric.module.artist.dto.req.ArtistReqDto;
import com.art.cheric.module.artist.dto.res.ArtistContactResDto;
import com.art.cheric.module.artist.dto.res.ArtistResDto;
import com.art.cheric.module.artist.error.ArtistErrorCode;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistFileRepository artistFileRepository;
    private final UserService userService;
    private final ArtistDegreeService artistDegreeService;
    private final ArtistExhibitionService artistExhibitionService;
    private final ArtistArtStorageService artistArtStorageService;
    private final ArtistPrizeService artistPrizeService;
    private final ArtistResidenceService artistResidenceService;

    // 작가 등록
    @Transactional
    public Long postArtist(User user, ArtistReqDto artistReq) {
        ArtistBasicReqDto artistBasicReq = artistReq.artistBasicReq();

        // 작가 여부 검사
        checkThisUserIsNotArtist(user.getId());

        // 사용자 정보 업데이트
        userService.updateUserAsArtist(user, artistBasicReq);

        // 작가 등록
        ArtistContactReqDto artistContactReq = artistReq.artistContactReq();
        Artist artist = Artist.of(user,
                ArtistContact.of(artistContactReq.instagram(), artistContactReq.twitter(), artistContactReq.naverBlog(),
                        artistContactReq.email()));
        artistRepository.save(artist);

        // 작가 서류 등록
        for (String fileUrl : artistReq.fileUrl()) {
            artistFileRepository.save(ArtistFile.of(artist, fileUrl));
        }

        // 작가 학위 등록
        artistDegreeService.saveArtistDegree(artistReq.artistDegreeReqs(), artist);

        // 작가 전시 정보 등록
        artistExhibitionService.saveArtistExhibition(artistReq.artistExhibitionReqs(), artist);

        // 작가 소장처 정보
        artistArtStorageService.saveArtistArtStorage(artistReq.artistArtStorageReqs(), artist);

        // 작가 수상 정보
        artistPrizeService.saveArtistPrize(artistReq.artistPrizeReqs(), artist);

        // 작가 레지던시 정보
        artistResidenceService.saveArtistResidence(artistReq.artistResidenceReqs(), artist);

        return user.getId();
    }


    public void checkThisUserIsNotArtist(Long userId) {
        if (artistRepository.findByUserId(userId).isPresent()) {
            throw new AppException(ArtistErrorCode.ARTIST_ALREADY_EXIST);
        }
    }

    // 작가 상세 정보 반환
    public ArtistResDto getArtist(User user, Long artistUserId) {
        // 요청 id 에 따른 반환
        Long userId = user.getId();
        if (artistUserId != null) {
            userId = artistUserId;
        }

        // 작가 찾기
        Artist artist = findArtistByUserId(userId);

        // 작가 상태 검증
        checkArtistStateValid(artist);

        // 작가 연락망
        ArtistContact artistContact = artist.getArtistContact();

        // 작가 아이디
        Long artistId = artist.getId();

        // 조회 결과 반환
        return ArtistResDto.of(
                ArtistContactResDto.of(artistContact.getInstagram(),
                        artistContact.getTwitter(), artistContact.getNaverBlog(), artistContact.getEmail()),
                artistDegreeService.getArtistDegrees(artistId),
                artistExhibitionService.getArtistExhibitions(artistId),
                artistArtStorageService.getArtistArtStorages(artistId),
                artistPrizeService.getArtistPrizes(artistId),
                artistResidenceService.getArtistResidences(artistId)
        );

    }

    private static void checkArtistStateValid(Artist artist) {
        if (artist.getState() != ValidateState.VALID) {
            throw new AppException(ArtistErrorCode.ARTIST_NON_VALID);
        }
    }

    private Artist findArtistByUserId(Long userId) {
        return artistRepository.findByUserId(userId).orElseThrow(
                () -> new AppException(ArtistErrorCode.ARTIST_NOT_FOUND)
        );
    }
}
