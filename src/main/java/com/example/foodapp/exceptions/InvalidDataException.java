package com.example.foodapp.exceptions;

public class InvalidDataException extends RuntimeException {

    public static final int STATUS_CODE = 400;
    public static final String MESSAGE = "Invalid input data";
    public static final String MESSAGE_WITH_DETAIL = MESSAGE + "- %s";

    public static final String MESSAGE_EMPTY_INPUT_JSON = "Request body must not be empty!";
    public static final String MESSAGE_EMPTY_USERNAME = "Username is required";
    public static final String MESSAGE_INVALID_USERNAME = "Username must not be empty";
    public static final String MESSAGE_EMPTY_PASSWORD = "Password is required";
    public static final String MESSAGE_NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD = "New Password can not be same as the Old Password";
    public static final String MESSAGE_EMPTY_USER_ROLE = "User Role is required";
    public static final String MESSAGE_INVALID_USER_ROLE= "User Role must be either \"ADMIN\" or \"CUSTOMER\"";
    public static final String MESSAGE_INVALID_ITEM_ID = "Item ID must not be empty or '0'";
    public static final String MESSAGE_EMPTY_ITEM_NAME = "Item Name must not be empty";
    public static final String MESSAGE_INVALID_ITEM_PRICE = "Item Price must not be empty or '0'";
    public static final String MESSAGE_INVALID_QUANTITY = "Quantity must not be empty or '0'";

    public InvalidDataException(final String message) {
        super(String.format(MESSAGE_WITH_DETAIL, message));
    }
}
