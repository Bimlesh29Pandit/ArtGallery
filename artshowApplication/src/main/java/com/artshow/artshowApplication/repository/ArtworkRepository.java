package com.artshow.artshowApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artshow.artshowApplication.entity.Artwork;
import com.artshow.artshowApplication.entity.ArtworkStatus;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByStatus(ArtworkStatus status);
    List<Artwork> findByArtistUsername(String username);
}

