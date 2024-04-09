package com.example.foodapp.service;

import com.example.foodapp.constants.UserRoles;
import com.example.foodapp.dao.UserDao;
import com.example.foodapp.entities.User;
import com.example.foodapp.exceptions.InvalidDataException;
import com.example.foodapp.exceptions.UserNotFoundException;
import com.example.foodapp.exceptions.UsernameAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(User user) throws UsernameAlreadyExistException, InvalidDataException {
        //validation of parameters
        validateUser(user);

        //check if user with entered username exists or not
        if (userDao.existsById(user.getUsername())) {
            throw new UsernameAlreadyExistException(user.getUsername());
        }
        userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void getUserByUsername(String username) throws UserNotFoundException, InvalidDataException{
        //validate username
        validateUsername(username);
        userDao.findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void updateUser(String username, String password, String userRoles) throws UserNotFoundException,
            InvalidDataException{
        //check whether user with username exists or not
        getUserByUsername(username);

        //validate password and user roles
        if (!StringUtils.isNotBlank(password)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_PASSWORD);
        } else if (!StringUtils.isNotBlank(userRoles)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_USER_ROLE);
        } else if (!userRoles.equals(UserRoles.ADMIN.name()) && !userRoles.equals(UserRoles.CUSTOMER.name())) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_USER_ROLE);
        }

        //check if user with entered username exists or not
        if (userDao.existsById(username)) {
            //if exists, then set the new password and user role
            User existingUser = userDao.findById(username).orElse(null);
            if (existingUser != null) {
                if (existingUser.getPassword().equals(password)) {
                    throw new InvalidDataException(InvalidDataException.MESSAGE_NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD);
                } else {
                    existingUser.setPassword(password);
                }
                existingUser.setUserRoles(userRoles);
                userDao.save(existingUser);
            }
        } else {
            //else, throw exception that user does not exist
          throw new UserNotFoundException(username);
        }
    }

    @Override
    public void deleteUserByUsername(String username) throws UserNotFoundException, InvalidDataException {
        //validate username
        validateUsername(username);

        //check if user by entered username exists or not
        if (userDao.existsById(username)) {
            // if exists, delete the entry from DB
            userDao.deleteById(username);
        } else {
            // else, throw exception that user does not exist
            throw new UserNotFoundException(username);
        }
    }

    private void validateUsername(String username) throws InvalidDataException {
        if (!StringUtils.isNotBlank(username)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_USERNAME);
        }
    }

    private void validateUser(User user) throws InvalidDataException {
        if (!StringUtils.isNotBlank(user.getUsername())) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_USERNAME);
        } else if (!StringUtils.isNotBlank(user.getPassword())) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_PASSWORD);
        } else if (!StringUtils.isNotBlank(user.getUserRoles())) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_USER_ROLE);
        } else if (!user.getUserRoles().equals(UserRoles.ADMIN.name()) && !user.getUserRoles().equals(UserRoles.CUSTOMER.name())) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_USER_ROLE);
        }
    }
}

