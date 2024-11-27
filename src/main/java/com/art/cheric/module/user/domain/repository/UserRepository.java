package com.art.cheric.module.user.domain.repository;

import com.art.cheric.module.user.domain.entity.User;
import java.util.Optional;

import com.art.cheric.module.user.domain.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
	Optional<User> findByEmail(String email);
	Optional<User> findByIsValidateArtistFalseAndName(String name);
}
