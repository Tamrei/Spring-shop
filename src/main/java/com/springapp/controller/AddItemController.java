package com.springapp.controller;

import com.springapp.model.Item;
import com.springapp.service.ItemService;
import com.springapp.util.ImageResizer;
import com.springapp.util.ImageResizerImpl;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class AddItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "addItem", method = RequestMethod.GET)
    public Item item() {
        return new Item();
    }

    @RequestMapping(value="createItem", method = RequestMethod.POST)
    public ModelAndView createItem(@ModelAttribute("item") @Valid Item item, BindingResult result,
                             @RequestParam("file") MultipartFile file) {
        if(result.hasErrors()) {
            return new ModelAndView("addItem", "invalidInputData", "Woops! seams like you have some errors!");
        }

        try {
            itemService.addItemAndResizeImage(item, file, 240, 150);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No Image ???");
        }
        catch (NullPointerException e) {
            return new ModelAndView("addItem", "notAnImage", "Woops! seams like it's not an image!");
        }

        return new ModelAndView("redirect:/shop");
    }
}
