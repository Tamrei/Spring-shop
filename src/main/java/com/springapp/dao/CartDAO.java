package com.springapp.dao;


import com.springapp.model.Cart;

import java.util.List;

public interface CartDAO {
    public void addCart(Cart cart);
    public Cart getCart(long id);
    public void updateCart(Cart cart);
    public void deleteCart(long id);
    public List<Cart> getAllCarts();

    public List<Cart> getNotOrderedCartByCustomerName(String userName);
    public List<Cart> getOrderedCartByCustomerName(String userName);
    public List<Cart> getCartsForPurchase(long id);
    public List<Cart> getAllPurchasedCart();
}
