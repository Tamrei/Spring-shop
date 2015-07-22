package com.springapp.service.statistic;


import java.util.Map;

public interface StatisticService {

    public Map<String, Integer> getTotalPurchaseRatio();
    public Map<String, Long> getPurchaseRatioByCity();

}
