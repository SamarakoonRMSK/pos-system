package com.saman.controller;

import com.saman.exceptions.UserException;
import com.saman.mapper.UserMapper;
import com.saman.model.User;
import com.saman.payload.dto.UserDTO;
import com.saman.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception, UserException {
        User user = userService.getUserById(id);
        if(user == null){
            throw new UserException("User not found");
        }
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
