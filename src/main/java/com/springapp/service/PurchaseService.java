package com.springapp.service;


import com.springapp.exceptions.RunOutOfItemsException;
import com.springapp.model.Address;
import com.springapp.model.Cart;
import com.springapp.model.Item;
import com.springapp.model.Purchase;
import com.springapp.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PurchaseService {
    public void addPurchase(Purchase purchase);
    public List<Purchase> getPurchase();
    public Map<Item, Cart> getAllItemsInTheCart(long id);
    public Purchase getPurchase(long id);

    public void updatePurchase(Purchase purchase);

    public void makeOrder(Address address, String customerName) throws RunOutOfItemsException;

    public void changeOrderStatus(long id, String status);

    public List<Purchase> getAllPurchasesForCustomer(String customerName);



}
