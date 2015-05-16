package com.springapp.service;

import com.springapp.model.Item;

import java.util.List;

public interface ItemService {
    public List<Item> getAllItems();
    public void addItem(Item item);
    public Item getItem(long id);
    public void updateItem(Item item);
    public void deleteItem(long id);

    public void putItemInCart(long itemID, String customerName, long amount);
}
