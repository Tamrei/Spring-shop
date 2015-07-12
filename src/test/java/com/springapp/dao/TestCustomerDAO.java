package com.springapp.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.mysql.jdbc.Connection;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Customer;
import com.springapp.model.UserRoles;
import com.springapp.service.CustomerService;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.sql.DriverManager;

import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@SuppressWarnings("unchecked")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestCustomerDAO {

    @Autowired
    private CustomerDAO customerDAO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Transactional
    @DatabaseSetup("classpath:/db/model/dao/customerDAO/initialData.xml")
    public void testGetCustomerByUsername() {
        final long id = 1;
        final String username = "customer1";
        Customer customer = customerDAO.getCustomerByName(username);
        assertNotNull(customer);
        assertEquals(customer.getId(), id);
        assertEquals(customer.getUsername(), username);

        System.out.println(customer);
    }

    @Test
    @Transactional
    @DatabaseSetup("classpath:/db/model/dao/customerDAO/initialData.xml")
    public void testGetCustomerByID() {
        final long id = 2;
        final String username = "customer2";
        Customer customer = customerDAO.getByID(id);
        assertEquals(customer.getId(), id);
        assertEquals(customer.getUsername(), username);

        System.out.println(customer);
    }

    @Test
    @DatabaseSetup("classpath:/db/model/dao/customerDAO/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/dao/customerDAO/expectedData_addCustomer.xml")
    public void testAddCustomer() {
        Customer customer = new Customer();
        customer.setUsername("customer3");
        customer.setPassword("12345");
        customerDAO.addCustomer(customer);
    }


    @Test
    @DatabaseSetup("classpath:/db/model/dao/customerDAO/initialData.xml")
    @ExpectedDatabase("classpath:/com/springapp/dao/customerDAO/expectedData_afterDelete.xml")
    public void testDeleteCustomer() {
        final long id = 1;
        customerDAO.deleteCustomerByID(id);
    }

    @Test
    @DatabaseSetup("classpath:/db/model/dao/customerDAO/initialData.xml")
    public void testGetAllCustomers() {
        assertEquals(customerDAO.getAllCustomers().size(), 2);
    }

    @Test
    @DatabaseSetup("classpath:/db/model/dao/customerDAO/initialData.xml")
    @ExpectedDatabase("classpath:/com/springapp/dao/customerDAO/expectedData_afterUpdate.xml")
    public void testUpdateCustomer() throws Exception {
        final long id = 2;
        Customer customer = new Customer();
        customer.setUsername("customerUP");
        customer.setPassword("1234");
        customer.setRole(UserRoles.ADMIN);
        customer.setEnabled(true);
        customer.setId(id);
        customerDAO.updateCustomer(customer);
    }
}
