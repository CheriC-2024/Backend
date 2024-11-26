package com.art.cheric.module.collection.service;


import com.art.cheric.global.enums.BasicSortType;
import com.art.cheric.global.error.exception.AppException;
import com.art.cheric.module.art.domain.entity.Art;
import com.art.cheric.module.art.dto.req.ArtIdListReqDto;
import com.art.cheric.module.art.service.ArtService;
import com.art.cheric.module.collection.domain.entity.Collection;
import com.art.cheric.module.collection.domain.entity.CollectionArt;
import com.art.cheric.module.collection.domain.repository.CollectionArtRepository;
import com.art.cheric.module.collection.domain.repository.CollectionRepository;
import com.art.cheric.module.collection.dto.req.CollectionIdListReqDto;
import com.art.cheric.module.collection.dto.req.CollectionReqDto;
import com.art.cheric.module.collection.dto.res.CollectionArtResDto;
import com.art.cheric.module.collection.dto.res.CollectionResDto;
import com.art.cheric.module.collection.error.CollectionErrorCode;
import com.art.cheric.module.user.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionArtRepository collectionArtRepository;
    private final ArtService artService;

    // 컬렉션 생성
    @Transactional
    public Long postCollection(User user, CollectionReqDto collectionReq) {
        // 컬렉션 이름 검증
        checkCollectionNameUnique(user, collectionReq);

        // 작품 존재 및 유효성 검증
        Art art = artService.findArtByIdWithValidation(collectionReq.artId());

        // 컬렉션 생성
        Collection collection = collectionRepository.save(
                Collection.of(user, collectionReq.name(), collectionReq.description()));

        // 컬렉션 작품 생성
        collectionArtRepository.save(CollectionArt.of(art, collection));

        return collection.getId();
    }

    // 컬렉션 이름 중복 검증
    private void checkCollectionNameUnique(User user, CollectionReqDto collectionReq) {
        if (collectionRepository.findByUserIdAndName(user.getId(), collectionReq.name()).isPresent()) {
            throw new AppException(CollectionErrorCode.COLLECTION_NAME_DUPLICATE);
        }
    }

    // 컬렉션에 작품 추가
    @Transactional
    public void postCollectionArt(User user, Long collectionId, ArtIdListReqDto artIdListReq) {
        // 컬렉션 찾기
        Collection collection = findCollectionByIdAndUserId(user, collectionId);

        // 컬렉션에 작품 추가
        List<CollectionArt> collectionArts = new ArrayList<>();
        for (Long artId : artIdListReq.artIds()) {
            // 작품 유효성 확인
            Art art = artService.findArtByIdWithValidation(artId);

            // 작품 유일성 확인
            checkCollectionArtIsUnique(collectionId, artId);

            // 작품 추가
            collectionArts.add(CollectionArt.of(art, collection));
        }
        collectionArtRepository.saveAll(collectionArts);
    }

    // 작품 리스트에 없는 작품인지 확인
    private void checkCollectionArtIsUnique(Long collectionId, Long artId) {
        if (collectionArtRepository.findByCollectionIdAndArtId(collectionId, artId).isPresent()) {
            throw new AppException(CollectionErrorCode.COLLECTION_ART_DUPLICATED);
        }
    }

    // 컬렉션 존재 여부 확인
    private Collection findCollectionByIdAndUserId(User user, Long collectionId) {
        return collectionRepository.findByIdAndUserId(collectionId, user.getId()).orElseThrow(
                () -> new AppException(CollectionErrorCode.COLLECTION_NOT_FOUND)
        );
    }

    // 자신의 컬렉션 모두 조회
    public List<CollectionResDto> getSelfCollectionList(User user) {
        return collectionRepository.getCollection(user.getId());
    }

    // 컬렉션 id 별 조회
    public List<CollectionArtResDto> getSelfCollectionList(User user, CollectionIdListReqDto collectionIdListReq,
                                                           BasicSortType sortType) {
        checkIdsValid(user, collectionIdListReq);

        return collectionRepository.getCollectionByCollectionIds(user.getId(), collectionIdListReq.collectionIds(),
                sortType);
    }

    private void checkIdsValid(User user, CollectionIdListReqDto collectionIdListReq) {
        collectionIdListReq.collectionIds()
                .forEach(id -> collectionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new AppException(CollectionErrorCode.COLLECTION_NOT_FOUND)));
    }

}
