package com.art.cheric.module.exhibition.service;

import com.art.cheric.global.enums.ExhibitionOrderType;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.global.util.DateFormatUtil;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.domain.entity.ArtistArt;
import com.art.cheric.module.art.domain.entity.OwnArt;
import com.art.cheric.module.art.dto.res.ArtExhibitionResDto;
import com.art.cheric.module.art.dto.res.OwnArtResDto;
import com.art.cheric.module.art.service.ArtService;
import com.art.cheric.module.exhibition.domain.entity.Exhibition;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionArt;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionBackgroundColor;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionHeart;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionHit;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionReview;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionReviewHeart;
import com.art.cheric.module.exhibition.domain.entity.ExhibitionTheme;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionArtRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionBackgroundColorRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionHeartRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionHitRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionReviewHeartRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionReviewRepository;
import com.art.cheric.module.exhibition.domain.repository.ExhibitionThemeRepository;
import com.art.cheric.module.exhibition.dto.req.ExhibitionArtReqDto;
import com.art.cheric.module.exhibition.dto.req.ExhibitionReqDto;
import com.art.cheric.module.exhibition.dto.req.ExhibitionReviewReqDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionArtResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionListResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionReviewDetailResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionReviewListResDto;
import com.art.cheric.module.exhibition.dto.res.ExhibitionReviewResDto;
import com.art.cheric.module.exhibition.error.ExhibitionErrorCode;
import com.art.cheric.module.user.domain.entity.User;
import com.art.cheric.module.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ExhibitionService {
    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionThemeRepository exhibitionThemeRepository;
    private final ExhibitionArtRepository exhibitionArtRepository;
    private final ExhibitionHeartRepository exhibitionHeartRepository;
    private final ExhibitionReviewRepository exhibitionReviewRepository;
    private final ExhibitionReviewHeartRepository exhibitionReviewHeartRepository;
    private final ExhibitionBackgroundColorRepository exhibitionBackgroundColorRepository;
    private final ExhibitionHitRepository exhibitionHitRepository;
    private final ArtService artService;
    private final UserService userService;

    // 전시 생성
    @Transactional
    public Long postExhibition(User user, ExhibitionReqDto exhibitionReq) {
        // 전시 생성
        Exhibition exhibition = saveExhibitionEntity(user, exhibitionReq);

        // 전시 배경 유효성 체크
        checkExhibitionBackgroundValid(exhibitionReq, exhibition);

        // 전시 배경 저장
        saveExhibitionBackgroundColor(exhibitionReq, exhibition);

        // 테마 생성
        saveExhibitionThemes(exhibition, exhibitionReq.themes());

        // 작품 생성
        saveExhibitionArts(exhibition, exhibitionReq.exhibitionArtReqs());

        return exhibition.getId();
    }

    // 작품 배경 컬러 저장
    private void saveExhibitionBackgroundColor(ExhibitionReqDto exhibitionReq, Exhibition exhibition) {
        if (exhibitionReq.coverImgUrl() == null && exhibitionReq.exhibitionBackgroundType() != null
                && exhibitionReq.colors() != null) {
            exhibitionBackgroundColorRepository.saveAll(
                    getColorListToExhibitionBackgroundColor(exhibitionReq.colors(), exhibition));
        }
    }

    // 작품 배경 요청 dto 유효성 검증
    private void checkExhibitionBackgroundValid(ExhibitionReqDto exhibitionReq, Exhibition exhibition) {
        if (exhibition.getCoverImgUrl() == null && (exhibitionReq.exhibitionBackgroundType() == null
                || exhibitionReq.colors() == null)) {
            throw new AppException(ExhibitionErrorCode.EXHIBITION_BACKGROUND_INVALID);
        }
    }

    // color string List를 엔티티로 변환
    private List<ExhibitionBackgroundColor> getColorListToExhibitionBackgroundColor(List<String> colors,
                                                                                    Exhibition exhibition) {
        int[] num = {0};
        return colors.stream()
                .map(color -> ExhibitionBackgroundColor.of(
                        exhibition,
                        color,
                        ++num[0]
                ))
                .collect(Collectors.toList());
    }

    // 전시 저장
    private Exhibition saveExhibitionEntity(User user, ExhibitionReqDto exhibitionReq) {
        return exhibitionRepository.save(
                Exhibition.of(
                        user,
                        exhibitionReq.name(),
                        exhibitionReq.description(),
                        exhibitionReq.font(),
                        exhibitionReq.fontColor(),
                        exhibitionReq.coverImgUrl(),
                        exhibitionReq.exhibitionBackgroundType(),
                        exhibitionReq.musicUrl()
                )
        );
    }

    // 전시 테마 저장
    private void saveExhibitionThemes(Exhibition exhibition, List<String> themes) {
        List<ExhibitionTheme> exhibitionThemes = themes.stream()
                .map(theme -> ExhibitionTheme.of(exhibition, theme))
                .toList();
        exhibitionThemeRepository.saveAll(exhibitionThemes);
    }

    // 전시 작품 리스트 저장
    private void saveExhibitionArts(Exhibition exhibition, List<ExhibitionArtReqDto> exhibitionArtReqs) {
        int[] num = {0};
        List<ExhibitionArt> exhibitionArts = exhibitionArtReqs.stream()
                .map(artReq -> createExhibitionArt(exhibition, artReq, ++num[0]))
                .toList();

        exhibitionArtRepository.saveAll(exhibitionArts);
    }

    // 전시 작품 유효성 검증 및 엔티티 변환
    private ExhibitionArt createExhibitionArt(Exhibition exhibition, ExhibitionArtReqDto artReq, int num) {
        Art art = artService.findArtByIdWithValidation(artReq.artId());

        // TODO 작가 무료 / 유로 작품 사용 알림 날리기

        return ExhibitionArt.of(exhibition, art, artReq.description(),
                artReq.reasonForPurchase(), artReq.review(), num);
    }

    // 전시 내용 확인
    public ExhibitionResDto getExhibitionContent(Long exhibitionId) {
        // 전시 있는지 확인
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // 전시 작품 리스트
        List<ExhibitionArtResDto> exhibitionArtRess = getExhibitionArtRess(
                exhibitionArtRepository.findByExhibitionIdOrderByNum(exhibitionId));

        // 전시 최신 댓글
        ExhibitionReview exhibitionReview = exhibitionReviewRepository.findTopReviewByExhibitionId(exhibitionId);

        // 댓글이 있다면 처리
        ExhibitionReviewResDto exhibitionReviewRes = null;
        if (exhibitionReview != null) {
            exhibitionReviewRes = ExhibitionReviewResDto.of(exhibitionReview.getMessage(),
                    exhibitionReview.getUser().getName());
        }

        return ExhibitionResDto.of(
                exhibition.getDescription(),
                exhibition.getHeartCount(),
                exhibition.getHits(),
                exhibitionArtRess,
                userService.createUserResDto(exhibition.getUser()),
                exhibitionReviewRes
        );

    }

    // 전시 작품 dto 변환
    private List<ExhibitionArtResDto> getExhibitionArtRess(List<ExhibitionArt> exhibitionArts) {
        List<ExhibitionArtResDto> exhibitionArtRess = new ArrayList<>();

        // 전시 작품 dto로 변환하기
        for (ExhibitionArt exhibitionArt : exhibitionArts) {
            Art art = exhibitionArt.getArt();

            // 기본 데이터 준비
            String artistName;
            Long artPrice = null;

            // 작품 종류에 따른 연결
            if (art.isCollectorsArt()) {
                OwnArt ownArt = artService.findOwnArtByArtId(art.getId());
                artistName = ownArt.getArtistName();
                artPrice = ownArt.getPrice();
            } else {
                ArtistArt artistArt = artService.findArtistArtByArtId(art.getId());
                artistName = artistArt.getUser().getName();
            }

            // 작품 정보 가져오기
            exhibitionArtRess.add(
                    ExhibitionArtResDto.of(
                            exhibitionArt.getDescription(),
                            exhibitionArt.getReasonForPurchase(),
                            exhibitionArt.getReview(),
                            ArtExhibitionResDto.of(
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
                                    artService.getArtTypes(art.getArtParts()),
                                    OwnArtResDto.from(artPrice),
                                    art.getHeartCount()
                            )
                    )
            );
        }

        return exhibitionArtRess;
    }

    // 전시 id로 찾기
    private Exhibition findExhibitionById(Long exhibitionId) {
        return exhibitionRepository.findById(exhibitionId).orElseThrow(
                () -> new AppException(ExhibitionErrorCode.EXHIBITION_NOT_EXIST)
        );
    }

    // 전시 좋아요
    @Transactional
    public int postHeart(User user, Long exhibitionId) {
        // 유효한 전시인지 확인
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // 이미 하트 설정되어 있는지
        checkExhibitionHeartUnique(user.getId(), exhibitionId);

        // 하트 추가
        exhibitionHeartRepository.save(ExhibitionHeart.of(exhibition, user));

        // 하트 카운트 추가
        exhibition.plusHeartCount();

        // 전시 변경 정보 저장
        exhibitionRepository.save(exhibition);

        // TODO 전시 제작자에게 알림 날리기

        return exhibition.getHeartCount();
    }

    // 좋아요 한 이력이 없는지 확인
    private void checkExhibitionHeartUnique(Long userId, Long exhibitionId) {
        if (exhibitionHeartRepository.findByUserIdAndExhibitionId(userId, exhibitionId).isPresent()) {
            throw new AppException(ExhibitionErrorCode.EXHIBITION_HEART_ALREADY_EXIST);
        }
    }

    // 전시 좋아요 취소
    @Transactional
    public int deleteHeart(User user, Long exhibitionId) {
        // 유효한 전시인지 확인
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // 하트한 적이 있는지 확인
        ExhibitionHeart exhibitionHeart = exhibitionHeartRepository.findByUserIdAndExhibitionId(user.getId(),
                exhibitionId).orElseThrow(() -> new AppException(ExhibitionErrorCode.EXHIBITION_HEART_DOESNT_EXIST));

        // 하트 삭제
        exhibitionHeartRepository.delete(exhibitionHeart);

        // 하트 카운트 감소
        exhibition.minusHeartCount();

        // 작품 변경 정보 저장
        exhibitionRepository.save(exhibition);

        return exhibition.getHeartCount();
    }

    // 전시 댓글 생성
    @Transactional
    public void postExhibitionReview(User user, Long exhibitionId, ExhibitionReviewReqDto exhibitionReviewReq) {
        // 유효한 전시인지 확인
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // TODO 추후 내용 검증 로직 추가하기

        // 저장
        exhibitionReviewRepository.save(ExhibitionReview.of(
                exhibition,
                user,
                exhibitionReviewReq.message(),
                null
        ));

        // TODO 전시 제작자에게 알림 날리기
    }

    // 전시 대댓글 생성
    @Transactional
    public void postExhibitionReReview(User user, Long exhibitionId, Long reviewId,
                                       ExhibitionReviewReqDto exhibitionReviewReq) {
        // 유효한 전시인지 확인
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // TODO 추후 내용 검증 로직 추가하기

        // 대댓글 대상 해당 전시  댓글이 존재하는 지 확인하기
        ExhibitionReview exhibitionReview = findExhibitionReviewByIdAndExhibitionIdAndUserId(reviewId, exhibitionId,
                user.getId());

        // 저장
        exhibitionReviewRepository.save(ExhibitionReview.of(
                exhibition,
                user,
                exhibitionReviewReq.message(),
                exhibitionReview
        ));

        // TODO 전시 제작자에게 알림 날리기
        // TODO 전시 댓글 작성자에게 알림 날리기
    }

    // 전시 댓글 좋아요
    @Transactional
    public int postReviewHeart(User user, Long exhibitionId, Long reviewId) {
        // 유효한 전시 댓글인지 확인
        ExhibitionReview exhibitionReview = findExhibitionReviewByIdAndExhibitionIdAndUserId(reviewId, exhibitionId,
                user.getId());

        // 이미 하트 설정되어 있는지
        checkExhibitionReviewHeartUnique(user.getId(), reviewId);

        // 하트 추가
        exhibitionReviewHeartRepository.save(ExhibitionReviewHeart.of(exhibitionReview, user));

        // 하트 카운트 추가
        exhibitionReview.plusHeartCount();

        // 전시 변경 정보 저장
        exhibitionReviewRepository.save(exhibitionReview);

        // TODO 댓글 작성자에게 알림 날리기

        return exhibitionReview.getHeartCount();
    }

    private ExhibitionReview findExhibitionReviewByIdAndExhibitionIdAndUserId(Long reviewId, Long exhibitionId,
                                                                              Long userId) {
        return exhibitionReviewRepository.findByIdAndExhibitionIdAndUserId(reviewId, exhibitionId, userId)
                .orElseThrow(() -> new AppException(ExhibitionErrorCode.REVIEW_NOT_EXIST));
    }

    private void checkExhibitionReviewHeartUnique(Long userId, Long reviewId) {
        if (exhibitionReviewHeartRepository.findByExhibitionReviewIdAndUserId(reviewId, userId).isPresent()) {
            throw new AppException(ExhibitionErrorCode.EXHIBITION_REVIEW_HEART_ALREADY_EXIST);
        }
    }

    // 전시 댓글 좋아요 취소
    @Transactional
    public int deleteReviewHeart(User user, Long exhibitionId, Long reviewId) {
        // 유효한 전시 댓글인지 확인
        ExhibitionReview exhibitionReview = findExhibitionReviewByIdAndExhibitionIdAndUserId(reviewId, exhibitionId,
                user.getId());

        // 하트한 적이 있는지 확인
        ExhibitionReviewHeart exhibitionHeart = exhibitionReviewHeartRepository.findByExhibitionReviewIdAndUserId(
                        reviewId, user.getId())
                .orElseThrow(() -> new AppException(ExhibitionErrorCode.EXHIBITION_REVIEW_HEART_DOESNT_EXIST));

        // 하트 삭제
        exhibitionReviewHeartRepository.delete(exhibitionHeart);

        // 하트 카운트 감소
        exhibitionReview.minusHeartCount();

        // 작품 변경 정보 저장
        exhibitionReviewRepository.save(exhibitionReview);

        return exhibitionReview.getHeartCount();
    }

    // 전시 조회수 기능
    @Transactional
    public int postHits(User user, Long exhibitionId) {
        // 전시 있는지 확인
        Exhibition exhibition = findExhibitionById(exhibitionId);

        // 조회수 봤던 적 있는지 확인
        ExhibitionHit exhibitionHit = exhibitionHitRepository.findByUserIdAndExhibitionId(user.getId(), exhibitionId)
                .orElseGet(() -> ExhibitionHit.of(exhibition, user));

        // 조회수 증가
        exhibitionHit.plusCounts();
        exhibition.plusHits();

        // 조회수 저장
        exhibitionHitRepository.save(exhibitionHit);
        exhibitionRepository.save(exhibition);

        return exhibition.getHits();
    }

    public Page<ExhibitionListResDto> getExhibitions(Long artId, Long userId, ExhibitionOrderType order, int page,
                                                     int size) {

        Pageable pageable = PageRequest.of(page, size);


        Page<Exhibition> exhibitionPage = exhibitionRepository.getExhibitionsBySortAndFilterAndPaging(artId, userId,
                order, pageable);

        List<ExhibitionListResDto> exhibitionListRess = new ArrayList<>();
        for (Exhibition exhibition : exhibitionPage.getContent()) {
            List<String> colors = exhibitionBackgroundColorRepository.findByExhibitionIdOrderByNum(exhibition.getId());

            exhibitionListRess.add(
                    ExhibitionListResDto.of(
                            exhibition.getId(),
                            exhibition.getName(),
                            exhibition.getFont(),
                            exhibition.getFontColor(),
                            colors,
                            exhibition.getExhibitionBackgroundType(),
                            exhibition.getCoverImgUrl(),
                            exhibition.getExhibitionThemes().stream()
                                    .map(ExhibitionTheme::getTheme).toList(),
                            exhibition.getHeartCount(),
                            exhibition.getHits()
                    )
            );
        }


        return new PageImpl<>(exhibitionListRess, PageRequest.of((int) (exhibitionPage.getPageable().getOffset() / exhibitionPage.getPageable().getPageSize()), exhibitionPage.getPageable().getPageSize()), exhibitionPage.getTotalPages());
    }

    public Page<ExhibitionReviewListResDto> getExhibitionReviews(Long exhibitionId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ExhibitionReview> exhibitionReviewPage = exhibitionReviewRepository.findReviewsByExhibitionIdWithPaging(
                exhibitionId, pageable );

        List<ExhibitionReviewListResDto> exhibitionReviewListRess = new ArrayList<>();
        for (ExhibitionReview exhibitionReview : exhibitionReviewPage.getContent()) {
            exhibitionReviewListRess.add(
                    ExhibitionReviewListResDto.of(
                            exhibitionReview.getId(),
                            exhibitionReview.getMessage(),
                            exhibitionReview.getUser().getName(),
                            exhibitionReview.getHeartCount(),
                            exhibitionReviewRepository.countByExhibitionIdAndExhibitionReviewId(exhibitionId,
                                    exhibitionReview.getId()),
                            DateFormatUtil.formatLocalDateTime(exhibitionReview.getCreatedAt())
                    )
            );
        }

        return new PageImpl<>(exhibitionReviewListRess, pageable, exhibitionReviewPage.getTotalPages());
    }

    public ExhibitionReviewDetailResDto getExhibitionReviewsById(Long exhibitionId, Long reviewId) {
        ExhibitionReview exhibitionReview = exhibitionReviewRepository.findByIdAndExhibitionId(reviewId, exhibitionId)
                .orElseThrow(() -> new AppException(ExhibitionErrorCode.REVIEW_NOT_EXIST));

        // 전시 기본 댓글 조회
        ExhibitionReviewDetailResDto exhibitionReviewDetailRes = convertToDetailResDto(exhibitionReview);

        // 대댓글 리스트를 가져와서 재귀적으로 처리
        List<ExhibitionReviewDetailResDto> replies = getRepliesRecursively(reviewId);

        // 대댓글 정보를 메인 댓글 DTO에 추가
        return exhibitionReviewDetailRes.addExhibitionReviewListResDto(replies);
    }

    private List<ExhibitionReviewDetailResDto> getRepliesRecursively(Long parentReviewId) {
        // 대댓글을 조회
        List<ExhibitionReview> replies = exhibitionReviewRepository.findReplyById(parentReviewId);

        // 대댓글 각각에 대해 DTO 변환 및 재귀 호출
        List<ExhibitionReviewDetailResDto> replyDtos = new ArrayList<>();
        for (ExhibitionReview reply : replies) {
            ExhibitionReviewDetailResDto replyDto = convertToDetailResDto(reply);
            replyDto.addExhibitionReviewListResDto(getRepliesRecursively(reply.getId())); // 재귀 호출
            replyDtos.add(replyDto);
        }

        return replyDtos;
    }

    private ExhibitionReviewDetailResDto convertToDetailResDto(ExhibitionReview review) {
        return ExhibitionReviewDetailResDto.of(
                review.getId(),
                review.getMessage(),
                review.getUser().getName(),
                review.getHeartCount(),
                DateFormatUtil.formatLocalDateTime(review.getCreatedAt())
        );
    }
}
