package com.springapp.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.springapp.model.Item;
import com.springapp.model.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@SuppressWarnings("unchecked")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestCartService {

    @Autowired
    private CartService cartService;

    @Test
    @DatabaseSetup("classpath:/db/model/service/cartService/initialData.xml")
    public void testGetAllItemsInTheCart() {
        final String customerName = "customer1";
        Map<Item, Cart> map = cartService.getAllItemsInTheCart(customerName);

        assertNotNull(map);

        for(Map.Entry<Item, Cart> entry : map.entrySet()) {
            assertEquals(entry.getKey().getItemID(), entry.getValue().getItemID());
            assertEquals(entry.getValue().getOwnerUsername(), customerName);
            assertNull(entry.getValue().getPurchaseID());
        }
    }

    @Test
    @DatabaseSetup("classpath:/db/model/service/cartService/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/service/cartService/expectedData_setItemAmountInTheCart.xml")
    public void testSetItemAmountInTheCart() {
        cartService.setItemAmountInTheCart(2, 55);
    }
}
