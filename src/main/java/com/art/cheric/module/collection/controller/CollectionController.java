package com.art.cheric.module.collection.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.module.art.dto.req.ArtIdListReqDto;
import com.art.cheric.module.collection.dto.req.CollectionReqDto;
import com.art.cheric.module.collection.service.CollectionService;
import com.art.cheric.module.user.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
@Slf4j
public class CollectionController implements CollectionControllerDocs {
    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<ResponseDto> postCollection(@RequestAttribute("user") User user,
                                                      @RequestBody @Valid CollectionReqDto collectionReq) {
        Long collectionId = collectionService.postCollection(user, collectionReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201, collectionId + " 컬렉션이 생성되었습니다."));
    }

    @PostMapping("/{collectionId}/art")
    public ResponseEntity<ResponseDto> postCollectionArt(@RequestAttribute("user") User user,
                                                         @PathVariable("collectionId") Long collectionId,
                                                         @RequestBody @Valid ArtIdListReqDto artIdListReq) {
        collectionService.postCollectionArt(user, collectionId, artIdListReq);
        return ResponseEntity.status(201).body(ResponseDto.of(201));
    }
}
