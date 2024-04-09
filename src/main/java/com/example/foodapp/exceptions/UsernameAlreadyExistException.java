package com.example.foodapp.exceptions;

public class UsernameAlreadyExistException extends RuntimeException {

    public static final int STATUS_CODE = 409;
    public static final String MESSAGE = "Username already taken";
    public static final String MESSAGE_WITH_DETAIL = MESSAGE + " - " + "User with username \"%s\" already exists";

    public UsernameAlreadyExistException(final String username) {
        super(String.format(MESSAGE_WITH_DETAIL, username));
    }
}
