package com.springapp.controller;

import com.springapp.model.Item;
import com.springapp.model.ItemDelivery;
import com.springapp.service.ItemDeliveryService;
import com.springapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;


@Controller
public class StorageController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value="store")
    public String storage(ModelMap modelMap) {
        //modelMap.put("itemDeliveries", itemDeliveryService.getAllItemDeliveries());

        modelMap.put("items", itemService.getAllItems());

        return "store";
    }

    @Autowired
    private ItemDeliveryService itemDeliveryService;

    @RequestMapping(value="store/add/{itemID}")
    public ModelAndView addNewItemDelivery(@ModelAttribute("itemDelivery") @Valid ItemDelivery itemDelivery) {
        //for (ItemDelivery delivery : itemDeliveryService.getAllItemDeliveries()) {
        //    System.out.println(delivery.getItem().toString());
        //}

        //itemDelivery.setItem(itemService.getItem(itemDelivery.getItemID()));
        itemDeliveryService.addItemDelivery(itemDelivery);

        //System.out.println(itemDelivery.getItem().toString());

        return new ModelAndView("redirect:/store");
    }

}
