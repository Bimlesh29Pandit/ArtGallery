package com.artshow.artshowApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artshow.artshowApplication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
