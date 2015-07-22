package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
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
        modelMap.addAttribute("purchases", purchaseService.getPurchase());

        return "purchases";
    }

    @RequestMapping(value = "purchases/{purchaseID}", method = RequestMethod.GET)
    public String getPurchaseForAddress(@PathVariable("purchaseID") Integer id, ModelMap modelMap) {
        modelMap.addAttribute("address", purchaseService.getPurchase(id).getAddress());
        modelMap.addAttribute("purchases", purchaseService.getAllItemsInTheCart(id));

        return "order";
    }

    @RequestMapping(value = "purchases/update/{purchaseID}", method = RequestMethod.POST)
    public String updatePurchaseStatus(@PathVariable("purchaseID") Integer id, @RequestParam("status") String status) {
        purchaseService.changePurchaseStatus(id, status);

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
}
