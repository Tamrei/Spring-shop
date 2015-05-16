package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Cart;
import com.springapp.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private GenericDAO itemDAO;

    @Autowired
    private GenericDAO purchaseDAO;

    @Autowired
    private CartDAO cartDAO;

    /**
     * This method adds purchase data in database
     *
     * @param itemID id of the item that was bought
     * @param customerName who bought this item
     * @param amount amount of the item
     */
    @Override
    @Transactional
    public void putItemInCart(long itemID, String customerName, long amount) {
        Cart newCart = new Cart(itemID, customerName, amount);

        for(Cart cart : cartDAO.getNotOrderedCartByCustomerName(customerName)) {
            if(cart.getItemID() == newCart.getItemID()) {
                cart.setAmount(newCart.getAmount() + cart.getAmount());
                purchaseDAO.update(cart);
                return;
            }
        }
        purchaseDAO.add(newCart);
    }

    @Override
    @Transactional
    public List<Item> getAllItems() {
        return itemDAO.getAll();
    }

    @Override
    @Transactional
    public void deleteItem(long id){
        itemDAO.delete(id);
    }

    @Override
    @Transactional
    public void addItem(Item item) {
        itemDAO.add(item);
    }

    @Override
    @Transactional
    public Item getItem(long id) {
        return (Item) itemDAO.get(id);
    }

    @Override
    @Transactional
    public void updateItem(Item item) {
        itemDAO.update(item);
    }
}
