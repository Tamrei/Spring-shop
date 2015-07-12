package com.springapp.dao;


import com.springapp.model.Customer;
import com.springapp.model.Item;

import java.util.List;

public interface ItemDAO {

    public void addItem(Item item);
    public Item getByID(long id);
    public void updateItem(Item item);
    public void deleteItem(long id);
    public List<Item> getAllItems();

    public List<Item> getAllAvailableItems();
    public List<Item> getNotAvailableITems();
}
