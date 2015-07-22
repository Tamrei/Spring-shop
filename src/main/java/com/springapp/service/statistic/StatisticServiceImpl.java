package com.springapp.service.statistic;

import com.springapp.dao.CartDAO;
import com.springapp.dao.ItemDAO;
import com.springapp.model.Cart;
import com.springapp.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ItemDAO itemDAO;

    @Override
    public Map<String, Integer> getTotalPurchaseRatio() {
        List<Cart> carts = cartDAO.getAllPurchasedCart();

        Map<String, Integer> map = new HashMap<String, Integer>();

        for(Cart cart : carts) {
            Item item = itemDAO.getByID(cart.getItemID());
            Integer count = map.get(item.getItemName());
            if (count == null) {
                map.put(item.getItemName(), (int) cart.getAmount());
            } else {
                map.put(item.getItemName(), (int) cart.getAmount() + count);
            }
        }

        return map;
    }

    public Map<String, Long> getPurchaseRatioByCity() {
        List<Cart> carts = cartDAO.getAllPurchasedCart();

        Map<String, Long> map = new HashMap<String, Long>();

        for(Cart cart : carts) {
            //Item item = itemDAO.getByID(cart.getItemID());
            String city = cart.getPurchase().getAddress().getCity();
            Long count = map.get(city);
            if (count == null) {
                map.put(city, cart.getAmount());
            } else {
                map.put(city, cart.getAmount() + count);
            }
        }

        return map;
    }
}
