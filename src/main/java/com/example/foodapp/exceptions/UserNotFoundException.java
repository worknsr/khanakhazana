package com.example.foodapp.exceptions;

public class UserNotFoundException extends RuntimeException{
    public static final int STATUS_CODE = 404;
    public static final String MESSAGE = "User does not exist";
    public static final String MESSAGE_WITH_DETAIL = MESSAGE + " - " + "User with username \"%s\" does not exists";

    public UserNotFoundException(final String username) {
        super(String.format(MESSAGE_WITH_DETAIL, username));
    }
}
