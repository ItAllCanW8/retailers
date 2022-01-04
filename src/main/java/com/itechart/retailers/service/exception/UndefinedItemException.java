package com.itechart.retailers.service.exception;

public class UndefinedItemException extends Exception {
    public UndefinedItemException() {
        super();
    }

    public UndefinedItemException(Throwable cause) {
        super(cause);
    }

    public UndefinedItemException(String message) {
        super(message);
    }

    public UndefinedItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
