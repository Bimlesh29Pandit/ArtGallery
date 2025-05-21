package com.artshow.artshowApplication.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String medium;
    private String dimensions;
    private String styleTag; // Set by curator
    @Lob
    @Column(name = "image_data")//, columnDefinition = "BYTEA"
    private byte[] imageData;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ArtworkStatus status = ArtworkStatus.PENDING;

    @ManyToOne
    private User artist;

    @ManyToOne
    private User curator;

    private String curatorNote;

    @ElementCollection
    private List<String> tags = new ArrayList<>();
    @Builder.Default
    private LocalDateTime submittedAt = LocalDateTime.now();
}
