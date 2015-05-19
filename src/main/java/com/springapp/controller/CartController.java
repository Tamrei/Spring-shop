package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.*;
import com.springapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "cart")
    public String cartList(@ActiveUser User activeUser, ModelMap modelMap) {
        modelMap.addAttribute("cart", new Cart());
        modelMap.addAttribute("carts", cartService.getAllItemInTheCart(activeUser.getUsername()));

        return "cart";
    }

    @RequestMapping(value = "cart/update/{cartID}", method = RequestMethod.POST)
    public String updateItemAmountInCart(@PathVariable long cartID, @RequestParam("amount") long amount) {
        cartService.setItemAmountInTheCart(cartID, amount);

        return "redirect:/cart";
    }

    @RequestMapping(value = "cart/layOut/{itemID}", method = RequestMethod.DELETE)
    public String layOutItemFromTheCart(@PathVariable long itemID) {
        cartService.layOutItemFromCart(itemID);

        return "redirect:/cart";
    }
/*
    @ModelAttribute
    public void getAllStatuses(ModelMap modelMap, @ActiveUser User activeUser) {
        if(activeUser != null) {

            modelMap.addAttribute("cartCount", cartService.getAllItemInTheCart(activeUser.getUsername()).size());
            System.out.println(cartService.getAllItemInTheCart(activeUser.getUsername()).size());
        } else System.out.println("Customer NULL!!!");
    }
    */
}
