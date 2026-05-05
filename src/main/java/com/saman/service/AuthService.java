package com.saman.service;


import com.saman.exceptions.UserException;
import com.saman.payload.dto.UserDto;
import com.saman.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto) throws UserException;
    AuthResponse login(UserDto userDto) throws UserException;
    
}
