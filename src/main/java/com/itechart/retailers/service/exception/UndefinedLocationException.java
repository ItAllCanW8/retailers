package com.itechart.retailers.service.exception;

public class UndefinedLocationException extends Exception {
    public UndefinedLocationException() {
        super();
    }

    public UndefinedLocationException(Throwable cause) {
        super(cause);
    }

    public UndefinedLocationException(String message) {
        super(message);
    }

    public UndefinedLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
