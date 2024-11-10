package com.art.cheric.module.user.domain.repository;

import com.art.cheric.module.user.domain.entity.UserPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPartRepository extends JpaRepository<UserPart, String> {
}
