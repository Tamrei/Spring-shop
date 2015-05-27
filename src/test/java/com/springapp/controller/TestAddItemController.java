package com.springapp.controller;

import com.springapp.model.Item;
import com.springapp.service.CartService;
import com.springapp.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import com.springapp.model.Address;
import com.springapp.service.CartService;
import com.springapp.service.PurchaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestAddItemController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private AddItemController addItemController;

    @Autowired
    @Mock
    private ItemService itemService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestAddItem() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", null, "image".getBytes());

        Item item = new Item();
        item.setItemName("TestName");
        item.setType("TestType");
        item.setPrice(999);
        item.setImage(file.getBytes());

        mockMvc.perform(fileUpload("/createItem").file(file)
                .param("itemName", "TestName")
                .param("type", "TestType")
                .param("price", "999"))
                .andExpect(redirectedUrl("/shop"));

        verify(itemService).addItemAndResizeImage(item, file, 240, 150);

    }

    @Test
    public void TestAddItem_NullFile() throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", null, "".getBytes());

        Item item = new Item();
        item.setItemName("TestName");
        item.setType("TestType");
        item.setPrice(999);
        item.setImage(file.getBytes());

        mockMvc.perform(fileUpload("/createItem").file(file)
                .param("itemName", "TestName")
                .param("type", "TestType")
                .param("price", "999"))
                .andExpect(redirectedUrl("/shop"));

        verify(itemService).addItemAndResizeImage(item, file, 240, 150);

    }
}
