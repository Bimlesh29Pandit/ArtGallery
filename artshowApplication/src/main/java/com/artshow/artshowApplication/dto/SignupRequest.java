package com.artshow.artshowApplication.dto;

import com.artshow.artshowApplication.entity.Role;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private Role role; // ARTIST or CURATOR
}

