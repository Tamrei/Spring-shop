package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.CustomerDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.exceptions.EmptyCartException;
import com.springapp.exceptions.NotAvailableItemException;
import com.springapp.model.*;
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
     *
     *
     * @param address      that is associated with the order
     * @param customerName username of order owner
     * @throws NotAvailableItemException
     */
    @Override
    @Transactional(rollbackFor={NotAvailableItemException.class})
    public void makePurchase(Address address, String customerName) throws NotAvailableItemException {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(customerName);

        if (carts.isEmpty()) {
            throw new EmptyCartException();
        }

        Purchase purchase = new Purchase();

        /* price of full purchase */
        double totalPrice = 0.0;

        address.setOwnerUsername(customerName);
        addressDAO.add(address);

        purchase.setAddress(address);
        purchaseDAO.add(purchase);

        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());

            checkForAvailability(cart, item);

            // carts with purchase id considered as a purchased
            cart.setPurchaseID(purchase.getPurchaseID());

            item.setLeftOnStore(item.getLeftOnStore() - cart.getAmount());

            // count the price
            totalPrice += item.getPrice() * cart.getAmount();
        }

        totalPrice = (double) Math.round(totalPrice * 1000) / 1000;
        purchase.setPrice(totalPrice);
    }

    private void checkForAvailability(Cart cart, Item item) throws NotAvailableItemException {
        if (item.getLeftOnStore() < cart.getAmount() || !item.isAvailable()) {
            throw new NotAvailableItemException();
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
