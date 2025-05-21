package com.artshow.artshowApplication.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.artshow.artshowApplication.dto.UploadArtworkRequest;
import com.artshow.artshowApplication.entity.Artwork;
import com.artshow.artshowApplication.entity.ArtworkStatus;
import com.artshow.artshowApplication.entity.User;
import com.artshow.artshowApplication.repository.ArtworkRepository;
import com.artshow.artshowApplication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepo;
    private final UserRepository userRepo;

    public Artwork uploadArtwork(String username, MultipartFile imageFile, UploadArtworkRequest meta) throws IOException {
        User artist = userRepo.findByUsername(username).orElseThrow();

        Artwork artwork = Artwork.builder()
                .title(meta.getTitle())
                .description(meta.getDescription())
                .medium(meta.getMedium())
                .dimensions(meta.getDimensions())
                .artist(artist)
                .imageData(imageFile.getBytes())
                .build();

        return artworkRepo.save(artwork);
    }

    public List<Artwork> getArtistSubmissions(String username) {
        return artworkRepo.findByArtistUsername(username);
    }

    public List<Artwork> getPendingSubmissions() {
        return artworkRepo.findByStatus(ArtworkStatus.PENDING);
    }
}

