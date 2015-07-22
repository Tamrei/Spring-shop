package com.springapp.controller;

import com.springapp.model.City;
import com.springapp.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "city")
    public String testPage(ModelMap modelMap) {
        modelMap.addAttribute("cities", cityService.getAllSupportedCities());

        return "city";
    }

    @RequestMapping(value="city/add")
    public ModelAndView addNewItemDelivery(@ModelAttribute("city") @Valid City city) {
        cityService.addSupportedCity(city);

        return new ModelAndView("redirect:/city");
    }
}
