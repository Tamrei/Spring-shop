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
    @DatabaseSetup("classpath:/db/model/dao/genericDAO/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/dao/genericDAO/expectedData_add.xml")
    public void testAdd() {
        Address testAddress = new Address();
        addressDAO.add(testAddress);
    }

    @Test
    @Transactional
    @DatabaseSetup("classpath:/db/model/dao/genericDAO/initialData.xml")
    public void testGet() {
        final long id = 1;
        Address address = (Address) addressDAO.get(id);
        assertNotNull(address);
        assertEquals(address.getAddressID(), id);
        assertEquals(address.getStreet(), "testStreet1");
        assertEquals(address.getCity(), "testCity1");
    }

    @Test
    @DatabaseSetup("classpath:/db/model/dao/genericDAO/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/dao/genericDAO/expectedData_delete.xml")
    public void testDelete() {
        addressDAO.delete((long)1);
    }

    @Test
    @DatabaseSetup("classpath:/db/model/dao/genericDAO/initialData.xml")
    public void testGetAll() {
        assertEquals(addressDAO.getAll().size(), 2);
    }

    @Test
    @Transactional
    @DatabaseSetup("classpath:/db/model/dao/genericDAO/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/dao/genericDAO/expectedData_update.xml")
    public void testUpdate() {
        Address address = new Address();
        address.setCity("cityAfterUpdate");
        address.setOwnerUsername("customer2");
        address.setStreet("testStreet2");
        address.setAddressID((long)2);
        addressDAO.update(address);
    }
}
