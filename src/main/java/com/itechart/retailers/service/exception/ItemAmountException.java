package com.itechart.retailers.service.exception;

public class ItemAmountException extends Exception{
    public ItemAmountException() {
        super();
    }
    public ItemAmountException(Throwable cause) {
        super(cause);
    }

    public ItemAmountException(String message) {
        super(message);
    }

    public ItemAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
