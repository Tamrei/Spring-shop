package com.springapp.service;

import com.springapp.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ItemService {
    public List<Item> getAllItems();
    public List<Item> getAllAvailableItems();
    public List<Item> getAllNotAvailableItems();

    public void addItem(Item item);
    public Item getItem(long id);
    public void updateItem(Item item);
    public void deleteItem(long id);

    public boolean enableDisableItem(long id);
    public void putItemInCart(long itemID, String customerName, long amount);
    public void addItemAndResizeImage(Item item, byte[] image, int width, int height) throws IOException;
}
