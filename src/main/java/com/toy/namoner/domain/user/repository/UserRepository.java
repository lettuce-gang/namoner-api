package com.toy.namoner.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toy.namoner.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByPhone(String phone);

}
