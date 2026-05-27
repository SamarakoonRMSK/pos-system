package com.saman.payload.response;

import com.saman.payload.dto.UserDTO;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDTO user;
    
}
