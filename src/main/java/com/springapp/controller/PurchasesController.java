package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.Purchase;
import com.springapp.model.PurchaseStatus;
import com.springapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class PurchasesController {

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "purchases")
    public String getPurchaseList(ModelMap modelMap) {
        modelMap.put("purchase", new Purchase());
        modelMap.put("purchases", purchaseService.getPurchase());

        return "purchases";
    }

    @RequestMapping(value = "purchases/{id}", method = RequestMethod.GET)
    public String getPurchaseForAddress(@PathVariable("id") Integer id, ModelMap modelMap) {
        modelMap.addAttribute("address", purchaseService.getPurchase(id).getAddress());
        modelMap.addAttribute("purchases", purchaseService.getAllItemsInTheCart(id));

        return "order";
    }

    @RequestMapping(value = "purchases/update/{purchaseID}", method = RequestMethod.PUT)
    public String updateOrderStatus(@PathVariable("purchaseID") Integer id, @RequestParam("status") String status) {
        purchaseService.changeOrderStatus(id, status);

        return "redirect:/purchases";
    }

    @RequestMapping(value = "/myPurchases", method = RequestMethod.GET)
    public String getPurchasesForCurrentCustomer(ModelMap modelMap, @ActiveUser User activeUser) {
        modelMap.addAttribute("purchases", purchaseService.getAllPurchasesForCustomer(activeUser.getUsername()));

        return "purchases";
    }

    @ModelAttribute
    public void getAllStatuses(ModelMap modelMap) {
        modelMap.addAttribute("orderStatus", PurchaseStatus.values());
    }

    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void getAllStatuses(ModelMap modelMap, @ActiveUser User activeUser) {
        if(activeUser != null) {

            modelMap.addAttribute("cartCount", cartService.getAllItemInTheCart(activeUser.getUsername()).size());
            System.out.println(cartService.getAllItemInTheCart(activeUser.getUsername()).size());
        } else System.out.println("Customer NULL!!!");
    }

}
