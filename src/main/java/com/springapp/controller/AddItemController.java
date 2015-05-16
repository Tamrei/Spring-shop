package com.springapp.controller;

import com.springapp.model.Item;
import com.springapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AddItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "addItem", method = RequestMethod.GET)
    public Item item() {
        return new Item();
    }

    @RequestMapping(value="createItem", method = RequestMethod.POST)
    public String createItem(@ModelAttribute("item") @Valid Item item,
                             @RequestParam("file") MultipartFile file) {
        try {
            item.setItemName(item.getItemName());
            item.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemService.addItem(item);

        return "redirect:/shop";
    }
}
