package com.springapp.dao;

import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Cart;
import com.springapp.model.Customer;
import com.springapp.model.Item;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class ItemDAOImpl implements ItemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GenericDAO itemDAO;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addItem(Item item) {
        itemDAO.add(item);
    }

    @Override
    public Item getByID(long id) {
        return (Item) itemDAO.get(id);
    }

    @Override
    public void updateItem(Item item) {
        itemDAO.update(item);
    }

    @Override
    public void deleteItem(long id) {
        itemDAO.delete(id);
    }

    @Override
    public List<Item> getAllItems() {
        String sql = "from Item ";
        return getSession().createQuery(sql).setCacheable(true).list();
    }

    @Override
    public List<Item> getAllAvailableItems() {
        String sql = "from Item where available = true ";
        return getSession().createQuery(sql).setCacheable(true).list();
    }

    public List<Item> getAllNotAvailableItems(String customerName) {
        //Query query = getSession().createQuery("from Item where customerName = :customerName and purchaseID = null")
        //        .setCacheable(true);
        //query.setString("ownerUsername", ownerUsername);
        //return (List<Cart>) query.list();

        return null;
    }

}
