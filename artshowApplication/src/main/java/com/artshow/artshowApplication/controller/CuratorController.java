package com.artshow.artshowApplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.artshow.artshowApplication.entity.Artwork;
import com.artshow.artshowApplication.entity.ArtworkStatus;
import com.artshow.artshowApplication.repository.ArtworkRepository;
import com.artshow.artshowApplication.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/curator")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CURATOR')")
public class CuratorController {

    private final ArtworkRepository artworkRepo;
    private final UserRepository userRepo;

    // Get all pending artworks
    @GetMapping("/pending")
    @Transactional
    public List<Artwork> getPendingArtworks() {
        return artworkRepo.findByStatus(ArtworkStatus.PENDING);
    }

    // Approve an artwork
    @PostMapping("/{artworkId}/approve")
    public ResponseEntity<String> approveArtwork(@PathVariable Long artworkId,
                                                 @RequestParam String note,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Artwork artwork = artworkRepo.findById(artworkId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found"));

        artwork.setStatus(ArtworkStatus.APPROVED);
        artwork.setCuratorNote(note);
        artwork.setCurator(userRepo.findByUsername(userDetails.getUsername()).orElseThrow());
        artworkRepo.save(artwork);

        return ResponseEntity.ok("Artwork approved");
    }

    // Reject an artwork
    @PostMapping("/{artworkId}/reject")
    public ResponseEntity<String> rejectArtwork(@PathVariable Long artworkId,
                                                @RequestParam String note,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        Artwork artwork = artworkRepo.findById(artworkId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found"));

        artwork.setStatus(ArtworkStatus.REJECTED);
        artwork.setCuratorNote(note);
        artwork.setCurator(userRepo.findByUsername(userDetails.getUsername()).orElseThrow());
        artworkRepo.save(artwork);

        return ResponseEntity.ok("Artwork rejected");
    }

    // Tag an artwork
    @PostMapping("/{artworkId}/tag")
    public ResponseEntity<String> tagArtwork(@PathVariable Long artworkId,
                                             @RequestBody List<String> tags) {
        Artwork artwork = artworkRepo.findById(artworkId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found"));

        artwork.getTags().addAll(tags);
        artworkRepo.save(artwork);

        return ResponseEntity.ok("Artwork tagged");
    }
}

