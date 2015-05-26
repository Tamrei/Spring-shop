package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebArgumentResolver;


@Controller
@SessionAttributes({"cartCount"})
public class NavBarController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cartCount", method = RequestMethod.GET)
    public
    @ResponseBody
    String getCartCount(@ActiveUser User activeUser) {
        System.out.println("request to get cartCount!");
        return cartService.getAllItemInTheCart(activeUser.getUsername()).size() + "";
    }
}
