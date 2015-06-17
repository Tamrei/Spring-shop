package com.springapp.service;

import com.springapp.model.Address;
import com.springapp.model.ItemDelivery;

import java.util.List;

public interface ItemDeliveryService {
    public void addItemDelivery(ItemDelivery itemDelivery);
    public List<ItemDelivery> getAllItemDeliveries();
    public ItemDelivery getItemDelivery(long id);
    public void updateItemDelivery(Address address);
    public void deleteItemDelivery(long id);
}
