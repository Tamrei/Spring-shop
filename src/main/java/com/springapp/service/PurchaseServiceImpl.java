package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.CustomerDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.exceptions.RunOutOfItemsException;
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
     * @param address      that is associated with the order
     * @param customerName of order owner
     */
    @Override
    @Transactional(rollbackFor={RunOutOfItemsException.class})
    public void makeOrder(Address address, String customerName) throws RunOutOfItemsException {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(customerName);

        Purchase purchase = new Purchase();
        double totalPrice = 0.0;

        address.setOwnerUsername(customerName);
        addressDAO.add(address);

        purchase.setAddress(address);
        purchaseDAO.add(purchase); //

        // find all items that are not purchased yet and purchase them (give them a purchase id)
        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());

            checkForAvailability(cart, item);

            cart.setPurchaseID(purchase.getPurchaseID());

            item.setLeftOnStore(item.getLeftOnStore() - cart.getAmount());
            // count the price
            totalPrice += item.getPrice() * cart.getAmount();
        }

        totalPrice = (double) Math.round(totalPrice * 1000) / 1000;
        purchase.setPrice(totalPrice);
    }


    private void checkForAvailability(Cart cart, Item item) throws RunOutOfItemsException{
        if (item.getLeftOnStore() < cart.getAmount()) {
            throw new RunOutOfItemsException();
        }
    }

    /*@Override
    @Transactional
    public Map <Pair<Cart, Item>, Long> getNotAvailableCarts(String username) {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(username);

        Map <Pair<Cart, Item>, Long> pairLongMap = new HashMap<Pair<Cart, Item>, Long>();

        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());
            long left = item.getLeftOnStore() - cart.getAmount();
            if (item.getLeftOnStore() < cart.getAmount()) {
                pairLongMap.put(new Pair(cart, item), left);
            }
        }

        return pairLongMap;
    }*/

    @Override
    @Transactional
    public Map <Cart, Item> getNotAvailableCarts(String username) {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(username);

        Map <Cart, Item> pairLongMap = new HashMap<Cart, Item>();

        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());
            if (item.getLeftOnStore() < cart.getAmount()) {
                pairLongMap.put(cart, item);
            }
        }

        return pairLongMap;
    }

    @Override
    public Map<HashMap<Cart, Item>, String> getNotAvailableCartsStr(String username) {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(username);

        Map <HashMap<Cart, Item>, String> pairLongMapStr = new HashMap<HashMap<Cart, Item>, String>();

        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());
            if (item.getLeftOnStore() < cart.getAmount()) {
                HashMap <Cart, Item> pairLongMap = new HashMap<Cart, Item>();
                pairLongMap.put(cart, item);
                String str = "";
                long left = item.getLeftOnStore();
                str += left;
                pairLongMapStr.put(pairLongMap, str);
            }
        }

        return pairLongMapStr;
    }

    @Override
    public Map<Pair<Cart, Item>, String> getNotAvailableCartsPair(String username) {
        List<Cart> carts = cartDAO.getNotOrderedCartByCustomerName(username);

        Map<Pair<Cart, Item>, String> pairLongMap = new HashMap<Pair<Cart, Item>, String>();

        for (Cart cart : carts) {
            Item item = (Item) itemDAO.get(cart.getItemID());
            if (item.getLeftOnStore() < cart.getAmount()) {
                String str = "";
                long left = item.getLeftOnStore();
                str += left;
                pairLongMap.put(new Pair(cart, item), str);
            }
        }

        return pairLongMap;
    }

    /**
     * this method change purchase status
     *
     * @param purchaseID of purchase to change
     * @param status     that we want to set
     */
    @Override
    @Transactional
    public void changeOrderStatus(long purchaseID, String status) {
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
