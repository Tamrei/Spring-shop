package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.CustomerDAO;
import com.springapp.dao.ItemDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.exceptions.EmptyCartException;
import com.springapp.exceptions.ItemNotAvailableException;
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
    private ItemDAO itemDAO;

    @Autowired
    private GenericDAO addressDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private GenericDAO cityDAO;

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
            Item item = itemDAO.getByID(cart.getItemID());
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
     * This method purchases all items which are located in this customer cart.
     *
     * Carts without purchase id are considered as a not purchased.
     *
     * @param address      that is associated with the order
     * @param customerName username of order owner
     * @throws ItemNotAvailableException
     */
    @Override
    @Transactional(rollbackFor={ItemNotAvailableException.class})
    public void makePurchase(Address address, String customerName) throws ItemNotAvailableException {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(customerName);

        if (carts.isEmpty()) {
            throw new EmptyCartException();
        }

        Purchase purchase = new Purchase();

        // price of full purchase
        double totalPrice = 0.0;

        address.setOwnerUsername(customerName);
        addressDAO.add(address);

        purchase.setAddress(address);
        purchaseDAO.add(purchase);

        for (Cart cart : carts) {
            Item item = itemDAO.getByID(cart.getItemID());

            checkForAvailability(cart, item);

            // carts with purchase id considered as a purchased
            cart.setPurchaseID(purchase.getPurchaseID());

            // decrease the item amount in the store by amount of items in this cart
            item.setLeftOnStore(item.getLeftOnStore() - cart.getAmount());

            // count the price
            totalPrice += item.getPrice() * cart.getAmount();
        }

        // round the number
        totalPrice = (double) Math.round(totalPrice * 1000) / 1000;
        purchase.setPrice(totalPrice);
    }

    private void checkForAvailability(Cart cart, Item item) throws ItemNotAvailableException {
        if (item.getLeftOnStore() < cart.getAmount() || !item.isAvailable()) {
            throw new ItemNotAvailableException();
        }
    }

    /**
     * This method change purchase status.
     *
     * @param purchaseID of purchase to change
     * @param status     that we want to set
     */
    @Override
    @Transactional
    public void changePurchaseStatus(long purchaseID, String status) {
        Purchase purchase = (Purchase) purchaseDAO.get(purchaseID);
        purchase.setStatus(PurchaseStatus.valueOf(status));
        purchaseDAO.update(purchase);
    }

    @Override
    @Transactional
    public List<City> getAllAvailableCities() {
        return cityDAO.getAll();
    }

    @Override
    @Transactional
    public List<Purchase> getAllPurchasesForCustomer(String customerName) {
        return new ArrayList<Purchase>(customerDAO.getCustomerByName(customerName).getPurchases());
    }
}
