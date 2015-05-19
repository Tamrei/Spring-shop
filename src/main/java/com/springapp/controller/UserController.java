package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.service.CartService;
import com.springapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("users")
    public String usersList(ModelMap modelMap)  {
        modelMap.put("users", customerService.getAllCustomers());
        return "users";
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.POST)
    public String enableDisableUser(@PathVariable Integer id) {
        customerService.enableDisableUser(id);
        return "redirect:";
    }

    @RequestMapping(value = "users/delete/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return "redirect:/users";
    }

}
