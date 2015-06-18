package com.springapp.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.springapp.exceptions.RunOutOfItemsException;
import com.springapp.model.Address;
import com.springapp.model.Cart;
import com.springapp.model.Item;
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

import java.util.HashMap;
import java.util.Map;

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
public class TestPurchaseService {

    @Autowired
    private PurchaseService purchaseService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/orderService/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/service/orderService/expectedData_makeOrder.xml")
    public void testMakeOrder() throws RunOutOfItemsException{
        final String customerName = "customer1";
        Address address = new Address(customerName, "Kiev", "My street 14");
        purchaseService.makeOrder(address, customerName);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/orderService/dataSet.xml")
    public void testGetAllItemsInTheCart() {
        final long id = 1;

        Map<Item, Cart> map = purchaseService.getAllItemsInTheCart(id);

        for(Map.Entry<Item, Cart> entry : map.entrySet()) {
            // check if the cartID that we are found match the cartID that we passed
            assertEquals((long) entry.getValue().getPurchaseID(), id);
            assertEquals(entry.getKey().getItemID(), entry.getValue().getItemID());
        }
    }
}
