package com.springapp.service.statistic;


import java.util.Map;

public interface StatisticService {
    public Map<String, Long> getTotalPurchaseStatistic();
    public Map<String, Map<String, Long>> getStatisticsForAllCities();
}
