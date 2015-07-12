package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.CustomerDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.exceptions.RunOutOfItemsException;
import com.springapp.exceptions.UserAlreadyExistsException;
import com.springapp.model.*;
import com.springapp.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings("unchecked")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private GenericDAO purchaseDAO;

    @Autowired
    private GenericDAO itemDAO;

    @Autowired
    private GenericDAO addressDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CartDAO cartDAO;

    @Override
    @Transactional
    public void addPurchase(Purchase purchase) {
        purchaseDAO.add(purchase);
    }

    @Override
    @Transactional
    public List<Purchase> getPurchase() {
        return purchaseDAO.getAll();
    }

    @Override
    @Transactional
    public Map<Item, Cart> getAllItemsInTheCart(long cartID) {
        Map<Item, Cart> map = new HashMap<Item, Cart>();

        for (Cart cart : cartDAO.getCartsForPurchase(cartID)) {
            Item item = (Item) itemDAO.get(cart.getItemID());
            map.put(item, cart);
        }

        return map;
    }

    @Override
    @Transactional
    public Purchase getPurchase(long id) {
        return (Purchase) purchaseDAO.get(id);
    }

    @Override
    @Transactional
    public void updatePurchase(Purchase purchase) {
        purchaseDAO.update(purchase);
    }

    /**
     * This method ...
     *
     * @param address      that is associated with the order
     * @param customerName username of order owner
     */
    @Override
    @Transactional(rollbackFor={RunOutOfItemsException.class})
    public void makeOrder(Address address, String customerName) throws RunOutOfItemsException {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(customerName);

        if (carts.isEmpty()) {
            //throw new EmptyStackException();
            return;
        }

        Purchase purchase = new Purchase();

        /* price of full purchase */
        double totalPrice = 0.0;

        address.setOwnerUsername(customerName);
        addressDAO.add(address);

        purchase.setAddress(address);
        purchaseDAO.add(purchase);

        /* find all items that are not purchased yet and purchase them (give them a purchase id) */
        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());

            checkForAvailability(cart, item);

            /* carts with purchase id considered as a purchased */
            cart.setPurchaseID(purchase.getPurchaseID());

            /*  */
            item.setLeftOnStore(item.getLeftOnStore() - cart.getAmount());
            /* count the price */
            totalPrice += item.getPrice() * cart.getAmount();
        }

        totalPrice = (double) Math.round(totalPrice * 1000) / 1000;
        purchase.setPrice(totalPrice);
    }

    private void checkForAvailability(Cart cart, Item item) throws RunOutOfItemsException{
        if (item.getLeftOnStore() < cart.getAmount() || !item.isAvailable()) {
            throw new RunOutOfItemsException();
        }
    }

    /**
     * This method change purchase status
     *
     * @param purchaseID of purchase to change
     * @param status     that we want to set
     */
    @Override
    @Transactional
    public void changeOrderStatus(long purchaseID, String status) {
        if (status.isEmpty()) {
            throw new IllegalArgumentException("Status is empty.");
        }

        Purchase purchase = (Purchase) purchaseDAO.get(purchaseID);
        purchase.setStatus(PurchaseStatus.valueOf(status));
        purchaseDAO.update(purchase);
    }

    @Override
    @Transactional
    public List<Purchase> getAllPurchasesForCustomer(String customerName) {
        return new ArrayList<Purchase>(customerDAO.getCustomerByName(customerName).getPurchases());
    }
}
