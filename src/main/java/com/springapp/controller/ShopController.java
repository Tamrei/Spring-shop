package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.Item;
import com.springapp.model.site.HomePageImage;
import com.springapp.service.CartService;
import com.springapp.service.ItemService;
import com.springapp.service.site.HomePageImageService;
import com.springapp.service.site.HomePageImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Controller
@SessionAttributes("cartCount")
public class ShopController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private HomePageImageService homePageImageService;

    @RequestMapping(value = "shop")
    public String itemsList(ModelMap modelMap) {
        modelMap.put("items", itemService.getAllAvailableItems());

        return "shop";
    }

    @RequestMapping(value = "/putItemInTheCart", method = RequestMethod.POST)
    public @ResponseBody
    void putItemInTheCart(@RequestParam("itemID") Integer itemID, @RequestParam("amount") long amount, @ActiveUser User activeUser) {
        itemService.putItemInCart(itemID, activeUser.getUsername(), amount);
    }

    @RequestMapping(value = "shop/img/{itemID}")
    @Transactional
    public void displayImage(@PathVariable Integer itemID, HttpServletResponse response) {
        Item item = itemService.getItem(itemID);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        try {
            response.getOutputStream().write(item.getImage());
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "shop/update/{itemID}", method = RequestMethod.POST)
    public String updateItem(@ModelAttribute("item") @Valid Item item,
                             @RequestParam("file") MultipartFile file) {
        try {
            item.setImage(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemService.updateItem(item);

        return "redirect:/shop";
    }

    @ModelAttribute
    public void getAllHomePageImages(ModelMap modelMap) {
        modelMap.addAttribute("homePageImages", homePageImageService.getAllHomePageImages());
    }
}
