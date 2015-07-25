package com.springapp.controller;

import com.springapp.service.statistic.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "statistic")
    public String testPage(ModelMap modelMap) {
        modelMap.addAttribute("totalStatistic", statisticService.getTotalPurchaseStatistic());
        modelMap.addAttribute("citiesStatisticMap", statisticService.getStatisticsForAllCities());

        return "statistic";
    }
}
