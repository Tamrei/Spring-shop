package com.springapp.service;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.springapp.model.Item;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TestItemService {

    @Autowired
    private ItemService itemService;

    @Test
    @DatabaseSetup("classpath:/db/model/service/itemService/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/service/itemService/expectedData_putItemInTheCart.xml")
    public void testPutItemInTheCart() {
        itemService.putItemInCart(2, "customer1", 50);
    }

    @Test
    @DatabaseSetup("classpath:/db/model/service/itemService/initialData.xml")
    @ExpectedDatabase("classpath:/db/model/service/itemService/expectedData_putItemInTheCart_sameItemInTheCart.xml")
    public void testPutItemInTheCart_sameItemInTheCart() {
        itemService.putItemInCart(1, "customer1", 50);
    }

    @Ignore
    @Test
    //@DatabaseSetup("classpath:/db/model/service/itemService/initialData.xml")
    public void testAddItemAndResizeImage() throws IOException {

        final int desiredWidth = 480;
        final int desiredHeight = 300;

        Item item = new Item();
        item.setItemName("TestName3");
        item.setType("TestType3");
        item.setLeftOnStore(100);

        final String path = getClass().getResource("test_image_350x282.jpg").getPath();

        BufferedImage image = ImageIO.read(new File(path));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);

        byte[] imageInByte = baos.toByteArray();

        itemService.addItemAndResizeImage(item, imageInByte, desiredWidth, desiredHeight);

        BufferedImage desiredImage = ImageIO.read(new ByteArrayInputStream(item.getImage()));

        assertEquals(desiredImage.getWidth(), desiredWidth);
        assertEquals(desiredImage.getHeight(), desiredHeight);
    }

}
