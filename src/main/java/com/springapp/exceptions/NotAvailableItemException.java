package com.springapp.exceptions;


public class NotAvailableItemException extends Exception {

    public String message;

    public NotAvailableItemException() {

    }

    public NotAvailableItemException(String message) {
        this.message = message;
    }

    public NotAvailableItemException(int itemsToPurchase, int itemLeftOnStore) {
        this.message = "Attempt to order " + itemsToPurchase + " items where there is only"
                + itemLeftOnStore + " left on store.";
    }

    @Override
    public String getMessage() {
        return message;
    }


}
