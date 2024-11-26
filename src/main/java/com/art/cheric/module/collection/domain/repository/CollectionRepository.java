package com.art.cheric.module.collection.domain.repository;

import com.art.cheric.module.collection.domain.entity.Collection;
import com.art.cheric.module.collection.domain.repository.custom.CollectionRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>, CollectionRepositoryCustom {
    Optional<Collection> findByUserIdAndName(Long userId, String name);
    Optional<Collection> findByIdAndUserId(Long collectionId, Long userID);
}
