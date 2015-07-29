package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class CartServiceImpl implements CartService {

    @Autowired
    private GenericDAO itemDAO;

    @Autowired
    private CartDAO cartDAO;

    @Override
    @Transactional
    public Map<Item, Cart> getAllItemsInTheCart(String customerName) {
        Map<Item, Cart> map = new HashMap<Item, Cart>();

        for (Cart cart : cartDAO.getNotOrderedCartByCustomerName(customerName)) {
            Item item = (Item) itemDAO.get(cart.getItemID());
            map.put(item, cart);
        }

        return map;
    }

    /**
     * Set the amount of items in the cart.
     *
     * @param cartID cart id to perform operation
     * @param amount that we want to set
     */
    @Override
    @Transactional
    public void setItemAmountInTheCart(long cartID, long amount) {
        if (amount <= 0) {
            cartDAO.deleteCart(cartID);
        } else {
            Cart cart = cartDAO.getCart(cartID);
            cart.setAmount(amount);
            cartDAO.updateCart(cart);
        }
    }

    @Override
    @Transactional
    public void layOutItemFromCart(long id) {
        cartDAO.deleteCart(id);
    }

    @Override
    @Transactional
    public Cart getCart(long id) {
        return cartDAO.getCart(id);
    }

    @Override
    @Transactional
    public List<Cart> getAllCarts() {
        return cartDAO.getAllCarts();
    }

    @Override
    @Transactional
    public void updateCart(Cart cart) {
        cartDAO.updateCart(cart);
    }

}