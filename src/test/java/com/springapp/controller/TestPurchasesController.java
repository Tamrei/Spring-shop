package com.springapp.controller;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.springapp.model.Address;
import com.springapp.service.ItemService;
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

import com.springapp.exceptions.UserAlreadyExistsException;
import com.springapp.model.Customer;
import com.springapp.model.UserRoles;
import com.springapp.service.CustomerService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestPurchasesController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private PurchasesController purchasesController;

    @Autowired
    @Mock
    private PurchaseService purchaseService;

    private TestingAuthenticationToken testingAuthenticationToken;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        User user = new User("Admin", "", AuthorityUtils.createAuthorityList("USER"));
        testingAuthenticationToken = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void testPurchaseList() throws Exception {
        mockMvc.perform(get("/purchases")
                .principal(testingAuthenticationToken))
                .andExpect(model().attributeExists("purchases"))
                .andExpect(view().name("purchases"));

        verify(purchaseService).getPurchase();
    }

    //@Test
    public void testGetPurchase() throws Exception {
        final long ID = 1;

        mockMvc.perform(get("purchases/{id}", ID)
                .principal(testingAuthenticationToken))
                .andDo(print())
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("purchases"))
                .andExpect(forwardedUrl("/WEB-INF/views/order.jsp"));

        verify(purchaseService).getPurchase(ID);
        verify(purchaseService).getAllItemsInTheCart(ID);
    }

    @Test
    public void testUpdateItemAmountInTheCart() throws Exception {
        final long ID = 1;

        mockMvc.perform(post("/purchases/update/{purchaseID}", ID)
                .principal(testingAuthenticationToken)
                .param("status", "SENT"))
                .andExpect(view().name("redirect:/purchases"));

        verify(purchaseService).changeOrderStatus(ID, "SENT");
    }

    @Test
    public void testGetMyPurchases() throws Exception {
        mockMvc.perform(get("/myPurchases")
                .principal(testingAuthenticationToken))
                .andExpect(model().attributeExists("purchases"));

        verify(purchaseService).getAllPurchasesForCustomer(testingAuthenticationToken.getName());
    }
}
