package com.springapp.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.springapp.model.ItemDelivery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TestItemDeliveryService {

    @Autowired
    private ItemDeliveryService itemDeliveryService;

    @Test
    @DatabaseSetup("classpath:/com/springapp/service/itemDeliveryService/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/service/itemDeliveryService/expectedData_addNewDelivery.xml")
    public void testAddPurchase_SamePurchaseInUserCart() throws Exception{
        ItemDelivery itemDelivery = new ItemDelivery();
        itemDelivery.setItemDeliveryID(1);
        itemDelivery.setItemID(1);
        itemDelivery.setItemQuantity(50);

        /* set date */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String dateInStr = "2015-06-16 16:41:47";
        Date date = sdf.parse(dateInStr);

        itemDelivery.setDateOfDelivery(date);


        itemDeliveryService.addItemDelivery(itemDelivery);
    }


}
