package com.springapp.exceptions;


public class ItemNotAvailableException extends RuntimeException {

    public String message;

    public ItemNotAvailableException() {

    }

    public ItemNotAvailableException(String message) {
        this.message = message;
    }

    public ItemNotAvailableException(int itemsToPurchase, int itemLeftOnStore) {
        this.message = "Attempt to order " + itemsToPurchase + " items where there is only"
                + itemLeftOnStore + " left on store.";
    }

    @Override
    public String getMessage() {
        return message;
    }


}
