package com.artshow.artshowApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artshow.artshowApplication.entity.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {}