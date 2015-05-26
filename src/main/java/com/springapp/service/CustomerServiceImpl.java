package com.springapp.service;

import com.springapp.dao.CustomerDAO;
import com.springapp.exceptions.UserAlreadyExistsException;
import com.springapp.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    /**
     * This method disable or enable the user selected by id
     *
     * @param customerID the user ID on which we want to perform the operation
     */
    @Override
    @Transactional
    public void enableDisableUser(long customerID) {
        Customer customer = customerDAO.getByID(customerID);
        if (customer.isEnabled()) {
            customer.setEnabled(false);
        } else customer.setEnabled(true);

        customerDAO.updateCustomer(customer);
    }

    /**
     * if user with the same username already exist in the system throws UserAlreadyExistsException
     * if username is unique add customer object to db (register new customer)
     *
     * @param customer instance that we want to register
     * @throws UserAlreadyExistsException if username not unique
     */
    @Override
    @Transactional
    public void registerNewCustomer(Customer customer) throws UserAlreadyExistsException {
        List<Customer> customerList = customerDAO.getAllCustomers();
        for (Customer customers : customerList) {
            if (customer.getUsername().equals(customers.getUsername())) {
                throw new UserAlreadyExistsException();
            }
        }
        customerDAO.addCustomer(customer);
    }

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(long id) {
        customerDAO.deleteCustomerByID(id);
    }

    @Override
    @Transactional
    public Customer getCustomer(long id) {
        return customerDAO.getByID(id);
    }

    @Override
    @Transactional
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    @Transactional
    public Customer getCustomer(String name) {
        return customerDAO.getCustomerByName(name);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

}