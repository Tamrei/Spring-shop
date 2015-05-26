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

    @Autowired
    private ItemService itemService;

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/service/purchaseService/expectedData_samePurchase.xml")
    public void testAddPurchase_SamePurchaseInUserCart () {
        itemService.putItemInCart(1, "customer1", 10);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/service/purchaseService/expectedData_noSamePurchase.xml")
    public void testAddPurchase_NoSamePurchaseInUserCart () {
        itemService.putItemInCart(2, "customer1", 5);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    public void testGetPurchases() {
        final String customerName = "customer1";
        Map<Item, Cart> map = cartService.getAllItemInTheCart(customerName);

        assertNotNull(map);

        for(Map.Entry<Item, Cart> entry : map.entrySet()) {
            assertEquals(entry.getKey().getItemID(), entry.getValue().getItemID());
            assertEquals(entry.getValue().getOwnerUsername(), customerName);
            assertNull(entry.getValue().getPurchaseID());
        }
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/purchaseService/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/service/purchaseService/expectedData_updatePurchase.xml")
    public void testUpdatePurchaseAmount() {
        cartService.setItemAmountInTheCart(2, 55);
    }
}
