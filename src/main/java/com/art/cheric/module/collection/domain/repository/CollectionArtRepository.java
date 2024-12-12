package com.art.cheric.module.collection.domain.repository;

import com.art.cheric.module.collection.domain.entity.CollectionArt;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionArtRepository extends JpaRepository<CollectionArt, Long> {
    Optional<CollectionArt> findByCollectionIdAndArtId(Long collectionId, Long artId);
    List<CollectionArt> findByCollectionId(Long collectionId);
}
