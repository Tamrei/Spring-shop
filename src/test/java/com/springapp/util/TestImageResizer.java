package com.springapp.util;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/resources/context/data-test.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TestImageResizer {

    @Ignore
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

        byte[] resizedImage = ImageResizer.resizeImage(imageInByte, desiredWidth, desiredHeight);

        BufferedImage desiredImage = ImageIO.read(new ByteArrayInputStream(resizedImage));

        assertEquals(desiredImage.getWidth(), desiredWidth);
        assertEquals(desiredImage.getHeight(), desiredHeight);
    }
}