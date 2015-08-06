package com.springapp.exceptions;

public class UserAlreadyExistsException extends Exception {

    private String message;

    public UserAlreadyExistsException() {

    }

    public UserAlreadyExistsException(String username) {
        this.message = "User with " + "'" + username + "'" + " username already exists.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
