package com.example.foodapp.service;

import com.example.foodapp.entities.User;
import com.example.foodapp.exceptions.InvalidDataException;
import com.example.foodapp.exceptions.UserNotFoundException;
import com.example.foodapp.exceptions.UsernameAlreadyExistException;

import java.util.List;

public interface UserService {
    void createUser(User user) throws UsernameAlreadyExistException, InvalidDataException;
    List<User> getAllUsers();
    void getUserByUsername(String username) throws UserNotFoundException, InvalidDataException;
    void updateUser(String username, String password, String userRoles) throws UserNotFoundException, InvalidDataException;
    void deleteUserByUsername(String username) throws UserNotFoundException, InvalidDataException;
}
