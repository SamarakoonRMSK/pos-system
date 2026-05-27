package com.saman.service;


import com.saman.exceptions.UserException;
import com.saman.payload.dto.UserDTO;
import com.saman.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDTO userDto) throws UserException;
    AuthResponse login(UserDTO userDto) throws UserException;
    
}
