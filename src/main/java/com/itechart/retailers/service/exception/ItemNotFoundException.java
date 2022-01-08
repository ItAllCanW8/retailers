package com.itechart.retailers.service.exception;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
