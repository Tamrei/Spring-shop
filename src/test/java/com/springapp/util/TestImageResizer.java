package com.springapp.util;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.springapp.model.Address;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestImageResizer {

    @Autowired
    private ImageResizer imageResizer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testResizeImage() throws IOException {
        final int desiredWidth = 480;
        final int desiredHeight = 300;

        final String path = getClass().getResource("test_image_350x282.jpg").getPath();

        System.out.println(path);

        BufferedImage image = ImageIO.read(new File(path));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);

        byte[] imageInByte = baos.toByteArray();

        byte[] resizedImage = imageResizer.resizeImage(imageInByte, desiredWidth, desiredHeight);

        BufferedImage desiredImage = ImageIO.read(new ByteArrayInputStream(resizedImage));

        assertEquals(desiredImage.getWidth(), desiredWidth);
        assertEquals(desiredImage.getHeight(), desiredHeight);
    }
}
