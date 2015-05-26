package com.springapp.model;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class TestModel {
    @Test
    @Transactional
    public void testSetAddress() {
        final String customerName = "customer1";
        final long id = 1;
        Address address = new Address();
        address.setAddressID(id);
        address.setOwnerUsername(customerName);

        Purchase purchase = new Purchase();
        purchase.setAddress(address);

        assertEquals(purchase.getAddressID(), id);
        assertEquals(purchase.getStatus(), PurchaseStatus.PROCESSED);
        assertEquals(purchase.getOwnerUsername(), customerName);
    }
}
