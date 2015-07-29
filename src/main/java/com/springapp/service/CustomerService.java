package com.springapp.service;

import com.springapp.exceptions.UserAlreadyExistsException;
import com.springapp.model.Customer;

import java.util.List;

public interface CustomerService {
    public void addCustomer(Customer customer);
    public void deleteCustomer(long id);
    public Customer getCustomer(long id);
    public Customer getCustomer(String name);
    public List<Customer> getAllCustomers();
    public void updateCustomer(Customer customer);

    public void registerNewCustomer(Customer customer) throws UserAlreadyExistsException;
    public void enableDisableUser(long id);

    public boolean enableDisableCustomer(long id);
}
