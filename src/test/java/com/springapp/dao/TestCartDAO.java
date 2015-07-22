package com.springapp.dao;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.springapp.model.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@SuppressWarnings("unchecked")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestCartDAO {

    @Autowired
    private CartDAO cartDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    //@DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    @DatabaseSetup("classpath:/db/model/dao/cartDAO/initialData.xml")
    public void testGetNotOrderedCarts() {
        final String customerName = "customer1";
        List<Cart> list = cartDAO.getNotOrderedCartByCustomerName(customerName);
        assertNotNull(list);

        for (Cart cart : list) {
            assertNull(cart.getPurchaseID());
            assertEquals(cart.getOwnerUsername(), customerName);
        }
    }

    @Test
    @Transactional
    //@DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    @DatabaseSetup("classpath:/db/model/dao/cartDAO/initialData.xml")
    public void testGetOrderedCarts() {
        final String customerName = "customer1";
        List<Cart> list = cartDAO.getOrderedCartByCustomerName(customerName);
        assertNotNull(list);

        for (Cart cart : list) {
            assertNotNull(cart.getPurchaseID());
            assertEquals(cart.getOwnerUsername(), customerName);
        }
    }

    @Test
    @Transactional
    //@DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    @DatabaseSetup("classpath:/db/model/dao/cartDAO/initialData.xml")
    public void testGetNotOrderedCartsByID() {
        final long id = 1;
        List<Cart> list = cartDAO.getCartsForPurchase(id);
        assertNotNull(list);

        for (Cart cart : list) {
            assertEquals((long)cart.getPurchaseID(), id);
        }
    }

    @Test
    @Transactional
    @DatabaseSetup("classpath:/db/model/dao/cartDAO/initialData.xml")
    public void testGetAllPurchasedCarts() {
        for (Cart cart : cartDAO.getAllPurchasedCart()) {
            System.out.println(cart);
        }
    }
}
