package com.springapp.controller;

import com.springapp.model.Item;
import com.springapp.model.ItemDelivery;
import com.springapp.service.ItemDeliveryService;
import com.springapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;


@Controller
public class StorageController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDeliveryService itemDeliveryService;

    @RequestMapping(value="store")
    public String storage(ModelMap modelMap) {
        modelMap.put("items", itemService.getAllItems());

        return "store";
    }

    @RequestMapping(value="store/add/{itemID}")
    public ModelAndView addNewItemDelivery(@ModelAttribute("itemDelivery") @Valid ItemDelivery itemDelivery) {
        itemDeliveryService.addItemDelivery(itemDelivery);

        return new ModelAndView("redirect:/store");
    }

    @RequestMapping(value = "/enableDisableItem", method = RequestMethod.POST)
    public @ResponseBody
    String enableDisableItem (@RequestParam("itemID") Integer id) {
        return Boolean.toString(itemService.enableDisableItem(id));
    }
}
