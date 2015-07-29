package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.Customer;
import com.springapp.model.Item;
import com.springapp.service.CartService;
import com.springapp.service.CustomerService;
import org.apache.avro.data.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("users")
    public String usersList(ModelMap modelMap)  {
        modelMap.put("users", customerService.getAllCustomers());

        return "users";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public @ResponseBody
    void deleteUser (@RequestParam("userID") Integer id) {
        customerService.deleteCustomer(id);
    }

    @RequestMapping(value = "/enableDisableUser", method = RequestMethod.POST)
    public @ResponseBody
    String enableDisableUser(@RequestParam("userID") Integer id) {
        return Boolean.toString(customerService.enableDisableCustomer(id));
    }

}
