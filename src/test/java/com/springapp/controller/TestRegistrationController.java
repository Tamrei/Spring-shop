package com.springapp.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/resources/spring/root-context.xml"})
public class TestRegistrationController {

    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    @InjectMocks
    private RegistrationController registrationController;

    @Autowired
    @Mock
    private CustomerService customerService;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSuccessRegistration() throws Exception {
        mockMvc.perform(post("/createUser")
                .param("username", "TestRegisterUser")
                .param("password", "1")
                .param("role", "USER"))
                .andExpect(model().hasNoErrors())
                .andExpect(forwardedUrl("/WEB-INF/views/login.jsp"));

        verify(customerService, times(1)).registerNewCustomer(new Customer("TestRegisterUser", "1", UserRoles.USER));
    }

    @Test
    public void testRegistrationUserExist() throws Exception {
        doThrow(new UserAlreadyExistsException())
                .when(customerService).registerNewCustomer(any(Customer.class));

        mockMvc.perform(post("/createUser")
                .param("username", "TestUserExist49")
                .param("password", "1")
                .param("role", "USER"))
                .andExpect(model().attributeExists("userExist"))
                .andExpect(forwardedUrl("/WEB-INF/views/registration.jsp"));

        verify(customerService, times(1)).registerNewCustomer(new Customer("TestUserExist49", "1", UserRoles.USER));
    }

    @Test
    public void testRegistrationErrorsInFields() throws Exception {
        mockMvc.perform(post("/createUser")
                .param("username", "")
                .param("password", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("someError"))
                .andExpect(forwardedUrl("/WEB-INF/views/registration.jsp"));

        verify(customerService, never()).registerNewCustomer(new Customer("", "", UserRoles.USER));
    }
}
