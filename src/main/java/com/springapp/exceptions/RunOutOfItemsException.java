package com.springapp.exceptions;


public class RunOutOfItemsException extends Exception {

    public String message;

    public RunOutOfItemsException() {

    }

    public RunOutOfItemsException(String message) {
        this.message = message;
    }

    public RunOutOfItemsException(int itemsToPurchase, int itemLeftOnStore) {
        this.message = "Attempt to order " + itemsToPurchase + " items where there is only"
                + itemLeftOnStore + " left on store.";
    }

    @Override
    public String getMessage() {
        return message;
    }


}
