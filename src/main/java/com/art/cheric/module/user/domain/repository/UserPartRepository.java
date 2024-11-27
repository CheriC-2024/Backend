package com.art.cheric.module.user.domain.repository;

import com.art.cheric.module.user.domain.entity.UserPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserPartRepository extends JpaRepository<UserPart, Long> {
    List<UserPart> findByUserId(Long userId);
}
