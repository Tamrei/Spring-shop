package com.springapp.exceptions;


public class EmptyCartException extends RuntimeException {

    private String message;

    public EmptyCartException() {
        this.message = "Cart is empty.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
