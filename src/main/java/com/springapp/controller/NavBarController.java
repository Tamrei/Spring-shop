package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes({"cartCount"})
public class NavBarController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/cartCount", method = RequestMethod.GET)
    public
    @ResponseBody
    String getCartCount(@ActiveUser User activeUser) {
        return cartService.getAllItemsInTheCart(activeUser.getUsername()).size() + "";
    }
}
