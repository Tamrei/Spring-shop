package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.Item;
import com.springapp.service.CartService;
import com.springapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class ShopController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "shop")
    public String itemsList(ModelMap modelMap) {
        modelMap.put("item", new Item());
        modelMap.put("items", itemService.getAllItems());

        return "shop";
    }

    @RequestMapping(value = "shop/{itemID}", method = RequestMethod.POST)
    public String putItemInTheCart(@PathVariable Integer itemID, @ActiveUser User activeUser,
                                   @RequestParam("amount") long amount) {
        itemService.putItemInCart(itemID, activeUser.getUsername(), amount);

        return "redirect:/shop";
    }

//
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemService.updateItem(item);

        return "redirect:/shop";
    }

    @Autowired
    private CartService cartService;

    @ModelAttribute(value = "cartCount")
    @PreAuthorize("isAuthenticated()")
    public void getCartCount(ModelMap modelMap, @ActiveUser User activeUser) {
        System.out.println("ACCESS!");
        try {
            modelMap.addAttribute("cartCount", cartService.getAllItemInTheCart(activeUser.getUsername()).size());
            System.out.println(cartService.getAllItemInTheCart(activeUser.getUsername()).size());
        } catch (Exception e) {System.out.println("Customer NULL!!!");}
    }

}
