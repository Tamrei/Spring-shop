package com.springapp.controller;

import com.springapp.exceptions.UserAlreadyExistsException;
import com.springapp.model.Customer;
import com.springapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public Customer customer() {
        return new Customer();
    }

    @RequestMapping(value="createUser", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("customer") @Valid Customer customer, BindingResult result) {
        if(result.hasErrors()) {
            return new ModelAndView("registration", "someError", "Some errors in your fields");
        }

        try {
            customerService.registerNewCustomer(customer);
            return new ModelAndView("login", "success", "Successfully registered as " + '"' + customer.getUsername() + '"');
        } catch (UserAlreadyExistsException e) {
            return new ModelAndView("registration", "userExist", "User with the same name already exists");
        }
    }
}
