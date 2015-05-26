package com.springapp.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.springapp.dao.CustomerDAO;
import com.springapp.exceptions.UserAlreadyExistsException;
import com.springapp.model.Customer;
import com.springapp.model.UserRoles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TestCustomerService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private CustomerService customerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/dao/customerDAO/dataSet.xml")
    @ExpectedDatabase("classpath:/com/springapp/service/customerService/expectedData_originalCustomer.xml")
    public void testAddCustomer() throws UserAlreadyExistsException {
        Customer customer = new Customer();
        customer.setUsername("customer4");
        customer.setPassword("12345");
        customerService.registerNewCustomer(customer);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/dao/customerDAO/dataSet.xml")
    @ExpectedDatabase("classpath:/com/springapp/service/customerService/expectedData_enableCustomer.xml")
    public void testEnableDisableCustomer_Enable() throws Exception{
        customerService.enableDisableUser((long)1);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/dao/customerDAO/dataSet.xml")
    @ExpectedDatabase("classpath:/com/springapp/service/customerService/expectedData_disableCustomer.xml")
    public void testEnableDisableCustomer_Disable() throws Exception{
        customerService.enableDisableUser((long)2);
    }

    @Test(expected = UserAlreadyExistsException.class)
    @DatabaseSetup("classpath:/com/springapp/dao/customerDAO/dataSet.xml")
    @ExpectedDatabase("classpath:/com/springapp/service/customerService/expectedData_sameCustomer.xml")
    public void testAddCustomer_sameCustomer() throws UserAlreadyExistsException {
        final String username = "customer3";

        Customer originalCustomer = new Customer();
        originalCustomer.setUsername(username);
        originalCustomer.setPassword("12345");

        Customer customerWithSameUsername = new Customer();
        customerWithSameUsername.setUsername(username);
        customerWithSameUsername.setPassword("54321");

        customerService.registerNewCustomer(originalCustomer);
        customerService.registerNewCustomer(customerWithSameUsername);
    }
}
