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
     * This method adds purchase data in database
     *
     * @param itemID       id of the item that was bought
     * @param customerName who bought this item
     * @param amount       amount of the item
     */
    @Override
    @Transactional
    public void putItemInCart(long itemID, String customerName, long amount) {
        Cart newCart = new Cart(itemID, customerName, amount);

        //updateItemQuantityOnStore(itemID, amount);

        for (Cart cart : cartDAO.getNotOrderedCartByCustomerName(customerName)) {
            if (cart.getItemID() == newCart.getItemID()) {
                cart.setAmount(newCart.getAmount() + cart.getAmount());
                purchaseDAO.update(cart);

                return;
            }
        }
        purchaseDAO.add(newCart);

        //----------------------------------------------------------------
        /*
        System.out.println("Item Service : ID:" + itemID);
        Item item = itemDAO.getByID(itemID);
        System.out.println("Item Service : Name:" + item.getItemName());
        System.out.println("Item Service : Amount (input):" + amount);
        System.out.println("Item Service : LeftOnStore: " + item.getLeftOnStore());
        System.out.println("Item Service : Result of (Left on store - amount) " + (item.getLeftOnStore() - amount));
        item.setLeftOnStore(item.getLeftOnStore() - amount);
        itemDAO.updateItem(item);
        */
    }

    private void updateItemQuantityOnStore(long itemID, long amount) {
        Item item = itemDAO.getByID(itemID);
        item.setLeftOnStore(item.getLeftOnStore() - amount);
        itemDAO.updateItem(item);
    }

    @Override
    @Transactional
    public List<Item> getAllItems() {
        return itemDAO.getAllAvailableItems();
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

    @Override
    @Transactional
    public void addItemAndResizeImage(Item item, MultipartFile image, int width, int height) throws IOException{
        item.setImage(imageResizer.resizeImage(image.getBytes(), width, height));
        itemDAO.addItem(item);
    }
}
