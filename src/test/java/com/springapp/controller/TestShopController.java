package com.springapp.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.springapp.model.Item;
import com.springapp.service.ItemService;
import com.springapp.service.ItemServiceImpl;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.mockito.Matchers.any;

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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestShopController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private ShopController shopController;

    @Autowired
    @Mock
    private ItemService itemService;

    private TestingAuthenticationToken testingAuthenticationToken;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        User user = new User("Tamrei", "", AuthorityUtils.createAuthorityList("USER"));
        testingAuthenticationToken = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    //@Ignore
    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/shop"))
                .andExpect(model().attributeExists("items"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/views/shop.jsp"));

        verify(itemService).getAllItems();
    }

    //@Ignore
    @Test
    public void TestUpdateItem() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image1.jpg", null, "image".getBytes());

        Item item = new Item();
        item.setItemName("TestName");
        item.setType("TestType");
        item.setPrice(999);
        item.setImage(file.getBytes());

        final long itemID = 1;

        mockMvc.perform(fileUpload("/shop/update/{itemID}", itemID).file(file)
                .param("itemName", "TestName")
                .param("type", "TestType")
                .param("price", "999"))
                .andExpect(redirectedUrl("/shop"));

        verify(itemService).updateItem(item);
    }

    //@Ignore
    @Test
    @SuppressWarnings("unchecked")
    public void testBuyItem() throws Exception {
        final long itemID = 1;

        mockMvc.perform(post("/shop/{itemID}", itemID)
                .principal(testingAuthenticationToken)
                .param("amount", "18"))
                .andExpect(view().name("redirect:/shop"));

        //verify(itemService).putItemToTheCart(itemID, "Test", 18);
    }
}
