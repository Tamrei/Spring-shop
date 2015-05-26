package com.springapp.service;


import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@SuppressWarnings("unchecked")
public class TestAddressService {

    //@Autowired
    @InjectMocks
    private AddressService addressService = new AddressServiceImpl();

    //@Autowired
    @Mock
    private GenericDAO addressDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddAddress() {
        Address address = new Address();
        addressService.addAddress(address);
        verify(addressDAO, times(1)).add(address);
    }

    @Test
    public void testGetAddress() {
        Address address = new Address();
        addressService.getAddress(address.getAddressID());
        verify(addressDAO, times(1)).get(address.getAddressID());
    }

    @Test
    public void testDeleteAddress() {
        Address address = new Address();
        addressService.deleteAddress(address.getAddressID());
        verify(addressDAO, times(1)).delete(address.getAddressID());
    }

    @Test
    public void testUpdateAddress() {
        Address address = new Address();
        addressService.updateAddress(address);
        verify(addressDAO, times(1)).update(address);
    }

    @Test
    public void testGetAllItems() {
        addressService.getAllAddresses();
        verify(addressDAO, times(1)).getAll();
    }
}
