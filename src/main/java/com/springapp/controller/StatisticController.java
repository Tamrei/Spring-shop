package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.service.PurchaseService;
import com.springapp.service.statistic.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "statistic")
    public String testPage(ModelMap modelMap) {
        modelMap.addAttribute("sts", statisticService.getTotalPurchaseRatio());
        modelMap.addAttribute("cityStatistic", statisticService.getPurchaseRatioByCity());

        return "statistic";
    }


}
