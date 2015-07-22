package com.springapp.dao;

import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Cart;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CartDAOImpl implements CartDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GenericDAO cartDAO;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addCart(Cart cart) {
        cartDAO.add(cart);
    }

    @Override
    public Cart getCart(long id) {
        return (Cart) cartDAO.get(id);
    }

    @Override
    @Transactional
    public void updateCart(Cart cart) {
        cartDAO.update(cart);
    }

    @Override
    public void deleteCart(long id) {
        cartDAO.delete(id);
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartDAO.getAll();
    }

    @Override
    @Transactional
    public List<Cart> getNotOrderedCartByCustomerName(String ownerUsername) {
        Query query = getSession().createQuery("from Cart where ownerUsername = :ownerUsername and purchaseID = null")
                .setCacheable(true);
        query.setString("ownerUsername", ownerUsername);
        return (List<Cart>) query.list();
    }

    @Override
    @Transactional
    public List<Cart> getOrderedCartByCustomerName(String ownerUsername) {
        Query query = getSession().createQuery("from Cart where ownerUsername = :ownerUsername and purchaseID != null")
                .setCacheable(true);
        query.setString("ownerUsername", ownerUsername);
        return (List<Cart>) query.list();
    }

    @Override
    @Transactional
    public List<Cart> getCartsForPurchase(long purchaseID) {
        Query query = getSession().createQuery("from Cart where purchaseID = :purchaseID")
                .setCacheable(true);
        query.setLong("purchaseID", purchaseID);
        return (List<Cart>) query.list();
    }

    @Override
    @Transactional
    public List<Cart> getAllPurchasedCart() {
        Query query = getSession().createQuery("from Cart where purchaseID != NULL")
                .setCacheable(true);
        return (List<Cart>) query.list();
    }
}
