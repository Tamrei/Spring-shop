package com.springapp.dao;

import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Customer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GenericDAO customerDAO;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Customer getCustomerByName(String s) {
        Query query = getSession().createQuery("from Customer where username = :username").setCacheable(true);
        query.setString("username", s);
        return (Customer) query.uniqueResult();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.add(customer);
    }

    @Override
    public Customer getByID(long id) {
        return (Customer) customerDAO.get(id);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

    @Override
    public void deleteCustomerByID(long id) {
        customerDAO.delete(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }
}
