package com.springapp.service;


import com.springapp.exceptions.ItemNotAvailableException;
import com.springapp.model.Address;
import com.springapp.model.Cart;
import com.springapp.model.Item;
import com.springapp.model.Purchase;

import java.util.List;
import java.util.Map;

public interface PurchaseService {
    public void addPurchase(Purchase purchase);
    public List<Purchase> getPurchase();
    public Map<Item, Cart> getAllItemsInTheCart(long id);
    public Purchase getPurchase(long id);

    public void updatePurchase(Purchase purchase);

    public void makePurchase(Address address, String customerName) throws ItemNotAvailableException;

    public void changeOrderStatus(long id, String status);

    public List<Purchase> getAllPurchasesForCustomer(String customerName);



}
