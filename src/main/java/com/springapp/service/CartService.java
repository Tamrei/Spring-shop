package com.springapp.service;

import com.springapp.model.Cart;
import com.springapp.model.Item;

import java.util.List;
import java.util.Map;

public interface CartService {
    public List<Cart> getAllCarts();

    public void layOutItemFromCart(long id);

    public Map<Item, Cart> getAllItemsInTheCart(String customerName);
    public Cart getCart(long id);
    public void updateCart(Cart cart);
    public void setItemAmountInTheCart(long id, long amount);
}
