package com.toy.namoner.domain.user.repository;

import com.toy.namoner.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByPhone(String phone);

}
