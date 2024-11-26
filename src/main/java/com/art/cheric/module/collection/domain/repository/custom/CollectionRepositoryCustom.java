package com.art.cheric.module.collection.domain.repository.custom;

import com.art.cheric.module.collection.dto.res.CollectionArtResDto;
import com.art.cheric.module.collection.dto.res.CollectionResDto;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepositoryCustom {
    List<CollectionResDto> getCollection(long userId);
    List<CollectionArtResDto> getCollectionByCollectionIds(long userId, List<Long> collectionIds);
}
