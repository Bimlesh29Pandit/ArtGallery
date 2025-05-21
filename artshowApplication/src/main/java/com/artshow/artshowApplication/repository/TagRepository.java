package com.artshow.artshowApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artshow.artshowApplication.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
