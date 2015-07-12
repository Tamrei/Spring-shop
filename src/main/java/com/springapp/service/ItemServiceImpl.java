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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ImageResizer imageResizer;

    /**
     * This method adds purchase data in database.
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
     * @param item item entity that we want to add
     * @param image image to resize
     * @param width desired width in pixels
     * @param height desired height in pixels
     * @throws IOException
     */
    @Override
    @Transactional
    public void addItemAndResizeImage(Item item, byte[] image, int width, int height) throws IOException {
        if(item.getLeftOnStore() > 0) {
            item.setAvailable(true);
        }

        item.setImage(imageResizer.resizeImage(image, width, height));
        itemDAO.addItem(item);
    }

    /**
     * Enable or disable item.
     *
     * @param id of item
     * @return the value that will be set
     */
    @Override
    @Transactional
    public String enableDisableItem(long id) {
        Item item = itemDAO.getByID(id);
        if (item.isAvailable()) {
            item.setAvailable(false);
            return "false";
        } else {
            item.setAvailable(true);
            return "true";
        }
    }

    /*
    private void updateItemQuantityOnStore(long itemID, long amount) {
        Item item = itemDAO.getByID(itemID);
        item.setLeftOnStore(item.getLeftOnStore() - amount);
        itemDAO.updateItem(item);
    }
    */

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
