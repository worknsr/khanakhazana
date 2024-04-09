package com.example.foodapp.exceptions;

public class ItemAlreadyExistsException extends RuntimeException {
    public static final int STATUS_CODE = 409;
    public static final String MESSAGE = "Item already exists";
    public static final String MESSAGE_ITEM_ID_EXIST = MESSAGE + " - " + "Item with itemId \"%d\" already exists.";
    public static final String MESSAGE_ITEM_NAME_EXIST = MESSAGE + " - " + "Item with itemName \"%s\" already exists.";

    public ItemAlreadyExistsException(final long itemId) {
        super(String.format(MESSAGE_ITEM_ID_EXIST, itemId));
    }

    public ItemAlreadyExistsException(final String itemName) {
        super(String.format(MESSAGE_ITEM_NAME_EXIST, itemName));
    }
}
