package com.springapp.service.statistic;

import com.springapp.dao.CartDAO;
import com.springapp.dao.ItemDAO;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Cart;
import com.springapp.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@SuppressWarnings("unchecked")
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private GenericDAO cityDAO;

    /**
     *
     * @return map of total purchase static for all items
     * where Key is itemName and Value is purchase count
     */
    @Override
    @Transactional
    public Map<String, Long> getTotalPurchaseStatistic() {
        return getStatistic(cartDAO.getAllPurchasedCart(), (c) -> true);
    }

    /**
     *
     * @return map of purchase statistic for each city
     * where Key is a city name and Value is a map
     * where Key is itemName and Value is purchase count
     */
    @Override
    @Transactional
    public Map<String, Map<String, Long>> getStatisticsForAllCities() {
        Map<String, Map<String, Long>> mapList = new HashMap<>();
        List<City> cities = cityDAO.getAll();

        for (City city : cities) {
            String cityName = city.getCityName();
            mapList.put(cityName, getStatistic(cartDAO.getAllPurchasedCart(), (c) -> c.getPurchase()
                    .getAddress()
                    .getCity().equals(cityName)));
        }
        return mapList;
    }

    @Transactional
    private Map<String, Long> getStatistic(List<Cart> carts, Predicate<Cart> selector) {
        Map<String, Long> map = new HashMap<>();

        for (Cart cart : carts) {
            if (selector.test(cart)) {
                String itemName = itemDAO.getByID(cart.getItemID()).getItemName();
                Long count = map.get(itemName);
                map.put(itemName, (count == null) ? cart.getAmount() : cart.getAmount() + count);
            }
        }
        return map;
    }
}
