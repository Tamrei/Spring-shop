package com.springapp.exceptions;


public class RunOutOfItemsException extends Exception {

    public String message;

    public RunOutOfItemsException() {

    }

    public RunOutOfItemsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
