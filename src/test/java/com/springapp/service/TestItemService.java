package com.springapp.service;


import com.springapp.dao.generic.GenericDAO;
import com.springapp.dao.generic.GenericDAOImpl;
import com.springapp.model.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@SuppressWarnings("unchecked")
public class TestItemService {

    //@Autowired
    @InjectMocks
    private ItemService itemService = new ItemServiceImpl();

    //@Autowired
    @Mock
    private GenericDAO itemDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddItem() {
        Item item = new Item();
        itemService.addItem(item);
        verify(itemDAO, times(1)).add(item);
    }

    @Test
    public void testGetItem() {
        Item item = new Item();
        itemService.getItem(item.getItemID());
        verify(itemDAO, times(1)).get(item.getItemID());
    }

    @Test
    public void testDeleteItem() {
        Item item = new Item();
        itemService.deleteItem(item.getItemID());
        verify(itemDAO, times(1)).delete(item.getItemID());
    }


    @Test
    public void testUpdateItem() {
        Item item = new Item();
        itemService.updateItem(item);
        verify(itemDAO, times(1)).update(item);
    }

    @Test
    public void testGetAllItems() {
        itemService.getAllItems();
        verify(itemDAO, times(1)).getAll();
    }


}
