package com.saman.service;

import com.saman.exceptions.UserException;
import com.saman.model.User;

import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;
    User getCurrentUser() throws UserException;
    User getUserByEmail(String email) throws UserException;
    User getUserById(Long id) throws Exception;
    List<User> getAllUsers();
}
