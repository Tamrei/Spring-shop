package com.springapp.service;

import com.springapp.dao.ItemDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Address;
import com.springapp.model.Item;
import com.springapp.model.ItemDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class ItemDeliveryServiceImpl implements ItemDeliveryService {

    @Autowired
    private GenericDAO itemDeliveryDAO;

    @Autowired
    private ItemDAO itemDAO;

    /**
     * This method add new item delivery.
     * and update item amount that available for customers to order.
     *
     * @param itemDelivery item entity.
     */
    @Override
    @Transactional
    public void addItemDelivery(ItemDelivery itemDelivery) {
        itemDeliveryDAO.add(itemDelivery);
        Item item = itemDAO.getByID(itemDelivery.getItemID());
        item.setLeftOnStore(item.getLeftOnStore() + itemDelivery.getItemQuantity());
    }

    @Override
    @Transactional
    public List<ItemDelivery> getAllItemDeliveries() {
        return itemDeliveryDAO.getAll();
    }

    @Override
    @Transactional
    public ItemDelivery getItemDelivery(long id) {
        return (ItemDelivery) itemDeliveryDAO.get(id);
    }

    @Override
    @Transactional
    public void updateItemDelivery(Address address) {
        itemDeliveryDAO.update(address);
    }

    @Override
    @Transactional
    public void deleteItemDelivery(long id) {
        itemDeliveryDAO.delete(id);
    }
}
