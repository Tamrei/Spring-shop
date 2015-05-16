package com.springapp.dao;


import com.springapp.model.Customer;

import java.util.List;

public interface CustomerDAO {
    public void addCustomer(Customer customer);
    public Customer getByID(long id);
    public void updateCustomer(Customer customer);
    public void deleteCustomerByID(long id);
    public List<Customer> getAllCustomers();

    public Customer getCustomerByName(String userName);
}
