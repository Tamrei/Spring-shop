package com.springapp.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.hibernate.search.indexes.serialization.javaserialization.impl.Add;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InitialContext;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@SuppressWarnings("unchecked")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestGenericDAO {

    @Autowired
    private GenericDAO addressDAO;

    @Test
    @Transactional
    @DatabaseSetup("classpath:/com/springapp/dao/genericDAO/dataSet.xml")
    public void testGetAddress() {
        long id = 1;
        Address address = (Address) addressDAO.get(id);
        assertNotNull(address);
        assertEquals(address.getAddressID(), id);
        assertEquals(address.getCity(), "testCity1");
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/dao/genericDAO/dataSet.xml")
    @ExpectedDatabase("classpath:/com/springapp/dao/genericDAO/expectedData_afterAdd.xml")
    public void testAddCustomer() {
        Address testAddress = new Address();
        addressDAO.add(testAddress);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/dao/genericDAO/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/dao/genericDAO/expectedData_afterDelete.xml")
    public void testDeleteCustomer() {
        addressDAO.delete((long)2);
    }

    @Test
    @DatabaseSetup("classpath:/com/springapp/dao/genericDAO/dataSet.xml")
    public void testGetAllUsers() {
        assertEquals(addressDAO.getAll().size(), 2);
    }

    @Test
    @Transactional
    @DatabaseSetup("classpath:/com/springapp/dao/genericDAO/dataSet.xml")
    @ExpectedDatabase(value = "classpath:/com/springapp/dao/genericDAO/expectedData_afterUpdate.xml")
    public void updateData() {
        Address address = new Address();
        address.setOwnerUsername("testName2");

        address.setCity("cityAfterUpdate");
        address.setStreet("testStreet2");
        address.setAddressID((long)2);
        addressDAO.update(address);
    }
}
