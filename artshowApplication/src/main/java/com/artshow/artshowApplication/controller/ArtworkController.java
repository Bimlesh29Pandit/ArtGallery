package com.artshow.artshowApplication.controller;

//import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.artshow.artshowApplication.dto.UploadArtworkRequest;
import com.artshow.artshowApplication.entity.Artwork;
import com.artshow.artshowApplication.service.ArtworkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/artist/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping(value = "/upload" ) //consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    public ResponseEntity<?> uploadArtwork(
        @RequestParam("image") MultipartFile image,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("medium") String medium,
        @RequestParam("dimensions") String dimensions,
        Principal principal
    ) throws IOException {

        UploadArtworkRequest meta = new UploadArtworkRequest();
        meta.setTitle(title);
        meta.setDescription(description);
        meta.setMedium(medium);
        meta.setDimensions(dimensions);

        Artwork saved = artworkService.uploadArtwork(principal.getName(), image, meta);
        return ResponseEntity.ok(Map.of("message", "Artwork uploaded!", "id", saved.getId()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Artwork>> getOwnSubmissions(Principal principal) {
        return ResponseEntity.ok(artworkService.getArtistSubmissions(principal.getName()));
    }
}

