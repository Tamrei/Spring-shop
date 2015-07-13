package com.springapp.controller;

import com.springapp.model.Address;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestMakePurchaseController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private MakePurchaseController makePurchaseController;

    @Autowired
    @Mock
    private PurchaseService purchaseService;

    private TestingAuthenticationToken testingAuthenticationToken;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        User user = new User("Test", "", AuthorityUtils.createAuthorityList("USER"));
        testingAuthenticationToken = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void testMakePurchase() throws Exception {
        final String testCity = "TestCity";
        final String testStreet = "TestStreet";
        Address address = new Address();
        address.setCity(testCity);
        address.setStreet(testStreet);

        mockMvc.perform(post("/makePurchase")
                .principal(testingAuthenticationToken)
                .param("city", testCity)
                .param("street", testStreet))
                .andExpect(view().name(("cart")));

        verify(purchaseService).makePurchase(address, testingAuthenticationToken.getName());
    }
}
