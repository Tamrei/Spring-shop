package com.springapp.controller;

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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/resources/spring/root-context.xml"})
public class TestCustomerController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private UserController userController;

    @Autowired
    @Mock
    private CustomerService customerService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUsersList() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("users"));

        verify(customerService).getAllCustomers();
    }



    /* AJAX methods */
    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(post("/deleteUser")
                .param("userID", "999"));
                //.andExpect(redirectedUrl("/users"));

        verify(customerService).deleteCustomer(999);
    }

    @Test
    public void testEnableDisableUser() throws Exception {
        mockMvc.perform(post("/enableDisableUser")
                .param("userID", "999"));

        verify(customerService).enableDisableCustomer(999);


    }




    /* Old methods */
    /*
    @Test
    public void testEnableDisableUser() throws Exception {
        final long updateID = 999;

        mockMvc.perform(post("/users/{id}", updateID))
                .andExpect(redirectedUrl("/users"));

        verify(customerService).enableDisableUser(updateID);
    }

    @Test
    public void testDeleteUser() throws Exception {
        final long deleteID = 999;

        mockMvc.perform(delete("/users/delete/{id}", deleteID))
                .andExpect(redirectedUrl("/users"));

        verify(customerService).deleteCustomer(deleteID);
    }
    */
    /* Old methods */
}
