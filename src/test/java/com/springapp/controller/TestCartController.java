package com.springapp.controller;

import com.springapp.service.CartService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestCartController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private CartController cartController;

    @Autowired
    @Mock
    private CartService cartService;

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
    public void testCartList() throws Exception {
        mockMvc.perform(get("/cart")
                .principal(testingAuthenticationToken))
                .andExpect(model().attributeExists("carts"))
                .andExpect(view().name("cart"));

        verify(cartService).getAllItemsInTheCart("Test");
    }

    @Test
    public void testUpdateItemAmountInTheCart() throws Exception {
        final long ID = 1;

        mockMvc.perform(post("/cart/update/{cartID}", ID)
                .principal(testingAuthenticationToken)
                .param("amount", "9"))
                .andExpect(redirectedUrl("/cart"));

        verify(cartService).setItemAmountInTheCart(ID, 9);
    }

    @Test
    public void testLayOutItemFromTheCart() throws Exception {
        final long ID = 1;

        mockMvc.perform(delete("/cart/layOut/{itemID}", ID)
                .principal(testingAuthenticationToken))
                .andExpect(redirectedUrl("/cart"));

        verify(cartService).layOutItemFromCart(ID);
    }
}
