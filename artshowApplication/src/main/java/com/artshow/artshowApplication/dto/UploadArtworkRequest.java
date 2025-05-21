package com.artshow.artshowApplication.dto;

import lombok.Data;

@Data
public class UploadArtworkRequest {
    private String title;
    private String description;
    private String medium;
    private String dimensions;
}

