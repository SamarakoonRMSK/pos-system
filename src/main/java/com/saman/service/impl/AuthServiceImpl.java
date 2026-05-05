package com.saman.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saman.configuration.JwtProvider;
import com.saman.domain.UserRole;
import com.saman.exceptions.UserException;
import com.saman.mapper.UserMapper;
import com.saman.model.User;
import com.saman.payload.dto.UserDto;
import com.saman.payload.response.AuthResponse;
import com.saman.repository.UserRepository;
import com.saman.service.AuthService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null){
            throw new UserException("Email already exists");
        }
        if(userDto.getRole().equals(UserRole.ROLE_ADMIN)){
            throw new UserException("Cannot assign admin role");
        }
        User newUser = new User();
        newUser.setFullName(userDto.getFullName());
        newUser.setEmail(userDto.getEmail());   
        newUser.setPhone(userDto.getPhone());
        newUser.setRole(userDto.getRole());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User registered successfully");
        authResponse.setUser(UserMapper.toDTO(savedUser));

        return authResponse;


    }

    @Override
    public AuthResponse login(UserDto userDto) throws UserException {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);
        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User logged in successfully");
        authResponse.setUser(UserMapper.toDTO(user));
        return authResponse;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
        if(userDetails == null){
            throw new UserException("email id doesn't exist"+email);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new UserException("Invalid password");
        }
        
        return new UsernamePasswordAuthenticationToken(email, password);
    }
    
}
