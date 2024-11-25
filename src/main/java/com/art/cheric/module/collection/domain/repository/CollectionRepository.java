package com.art.cheric.module.collection.domain.repository;

import com.art.cheric.module.collection.domain.entity.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Optional<Collection> findByUserIdAndName(Long userId, String name);
    Optional<Collection> findByIdAndUserId(Long collectionId, Long userID);
}
