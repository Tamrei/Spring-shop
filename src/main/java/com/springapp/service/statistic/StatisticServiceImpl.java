package com.springapp.service.statistic;

import com.springapp.dao.CartDAO;
import com.springapp.dao.ItemDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Cart;
import com.springapp.model.City;
import com.springapp.model.Item;
import com.springapp.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private GenericDAO cityDAO;

    @Override
    @Transactional
    public Map<String, Long> getTotalPurchaseStatistic() {
        List<Cart> carts = cartDAO.getAllPurchasedCart();
        Map<String, Long> map = new HashMap<String, Long>();

        for(Cart cart : carts) {
            String itemName = itemDAO.getByID(cart.getItemID()).getItemName();
            Long count = map.get(itemName);
            map.put(itemName, (count == null) ? cart.getAmount() : cart.getAmount() + count);
        }
        return map;
    }
/*
    @Override
    @Transactional
    public Map<String, Long> getPurchaseStatisticForAllCities() {
        List<Cart> carts = cartDAO.getAllPurchasedCart();
        Map<String, Long> map = new HashMap<String, Long>();

        for(Cart cart : carts) {
            String city = cart.getPurchase().getAddress().getCity();
            Long count = map.get(city);
            map.put(city, (count == null) ? cart.getAmount() : cart.getAmount() + count);
        }
        return map;
    }
*/
    @Override
    @Transactional
    public Map<String, Map<String, Long>> getStatisticsForAllCities() {
        Map<String, Map<String, Long>> mapList = new HashMap<String, Map<String, Long>>();
        List<City> cities = cityDAO.getAll();

        for (City city : cities) {
            mapList.put(city.getCityName(), getTotalPurchaseRatio(city.getCityName()));
        }
        return mapList;
    }

    private Map<String, Long> getTotalPurchaseRatio(String cityName) {
        List<Cart> carts = cartDAO.getAllPurchasedCart();
        Map<String, Long> map = new HashMap<String, Long>();

        for(Cart cart : carts) {
            if(cart.getPurchase().getAddress().getCity().equals(cityName)) {
                String itemName = itemDAO.getByID(cart.getItemID()).getItemName();
                Long count = map.get(itemName);
                map.put(itemName, (count == null) ? cart.getAmount() : cart.getAmount() + count);
            }
        }
        return map;
    }
}
