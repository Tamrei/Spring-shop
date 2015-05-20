package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes({"cartCount"})
public class NavBarController {

    @Autowired
    private CartService cartService;
/*
    @RequestMapping(method = RequestMethod.GET, value = "navbar")
    public String newItem(ModelMap modelMap, @ActiveUser User activeUser) {
        try {
            modelMap.addAttribute("cartCount", cartService.getAllItemInTheCart(activeUser.getUsername()).size());
        } catch (Exception e) {

        }

        return "static/navbar";
    }
*/
    /* AJAX KREYGAZM */
    @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
    public @ResponseBody
    String getTime(@ActiveUser User activeUser) {
        System.out.println("request!");
        return cartService.getAllItemInTheCart(activeUser.getUsername()).size() + "";
    }
}
