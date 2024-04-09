package com.example.foodapp.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public static final int STATUS_CODE = 404;
    public static final String MESSAGE = "Item does not exist";
    public static final String MESSAGE_WITH_DETAIL = MESSAGE + " - " + "Item with itemId \"%d\" does not exists";

    public ItemNotFoundException(final long itemId) {
        super(String.format(MESSAGE_WITH_DETAIL, itemId));
    }
}
