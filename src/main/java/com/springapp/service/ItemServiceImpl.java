package com.springapp.service;

import com.springapp.dao.CartDAO;
import com.springapp.dao.ItemDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Cart;
import com.springapp.model.Item;
import com.springapp.util.ImageResizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private GenericDAO purchaseDAO;

    @Autowired
    private CartDAO cartDAO;

    /**
     * This method creates new cart entity or update existing one that is associated with customer.
     *
     * New cart will be create only if customer don't have not ordered cart with same item (itemID).
     * if customer have not ordered cart with same item,
     * the existing cart's amount will be increased by @param amount.
     *
     * @param itemID       id of the item that was bought
     * @param customerName who bought this item
     * @param amount       amount of the item
     */
    @Override
    @Transactional
    public void putItemInCart(long itemID, String customerName, long amount) {
        Cart newCart = new Cart(itemID, customerName, amount);

        for (Cart cart : cartDAO.getNotOrderedCartByCustomerName(customerName)) {
            if (cart.getItemID() == newCart.getItemID()) {
                cart.setAmount(newCart.getAmount() + cart.getAmount());
                purchaseDAO.update(cart);

                return;
            }
        }
        purchaseDAO.add(newCart);
    }

    /**
     * Add new item in to the shop and resize it.
     *
     * set item status to true (enable) only if
     * param leftOnStore is greater than 0
     *
     * @param item   item entity that we want to add
     * @param image  image to resize
     * @param width  desired width in pixels
     * @param height desired height in pixels
     * @throws IOException
     */
    @Override
    @Transactional
    public void addItemAndResizeImage(Item item, byte[] image, int width, int height) throws IOException {
        if (item.getLeftOnStore() > 0) {
            item.setAvailable(true);
        }

        item.setImage(ImageResizer.resizeImage(image, width, height));
        itemDAO.addItem(item);
    }

    /**
     * Enable or disable item.
     *
     * @param id of item
     * @return boolean value that will be set
     */
    @Override
    @Transactional
    public boolean enableDisableItem(long id) {
        Item item = itemDAO.getByID(id);
        if (item.isAvailable()) {
            item.setAvailable(false);
            return false;
        }

        item.setAvailable(true);
        return true;
    }

    @Override
    @Transactional
    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    @Override
    @Transactional
    public List<Item> getAllAvailableItems() {
        return itemDAO.getAllAvailableItems();
    }

    @Override
    @Transactional
    public List<Item> getAllNotAvailableItems() {
        return itemDAO.getNotAvailableITems();
    }

    @Override
    @Transactional
    public void deleteItem(long id) {
        itemDAO.deleteItem(id);
    }

    @Override
    @Transactional
    public void addItem(Item item) {
        itemDAO.addItem(item);
    }

    @Override
    @Transactional
    public Item getItem(long id) {
        return (Item) itemDAO.getByID(id);
    }

    @Override
    @Transactional
    public void updateItem(Item item) {
        itemDAO.updateItem(item);
    }


}
