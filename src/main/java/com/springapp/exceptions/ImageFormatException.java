package com.springapp.exceptions;


public class ImageFormatException extends RuntimeException {

    private String message;

    public ImageFormatException() {
        this.message = "Image have wrong format.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
