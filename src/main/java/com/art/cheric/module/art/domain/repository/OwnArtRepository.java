package com.art.cheric.module.art.domain.repository;

import com.art.cheric.module.art.domain.entity.OwnArt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnArtRepository extends JpaRepository<OwnArt, Long> {

    Optional<OwnArt> findByUserIdAndArtId(Long userId, Long artId);
}
