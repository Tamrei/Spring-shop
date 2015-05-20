package com.springapp.util;


import com.springapp.exceptions.ImageFormatException;
import org.imgscalr.Scalr;
import org.springframework.stereotype.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Repository
public class ImageResizerImpl implements ImageResizer {

    @Override
    public byte[] resizeImage(byte[] image, int width, int height) throws IOException {
        BufferedImage newImage = getScaledInstance
                (ImageIO.read(new ByteArrayInputStream(image)), width, height, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img          the original image to be scaled
     * @param targetWidth  the desired width of the scaled instance,
     *                     in pixels
     * @param targetHeight the desired height of the scaled instance,
     *                     in pixels
     * @param hint         one of the rendering hints that corresponds to
     *                     {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *                     {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *                     {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *                     {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     */
    private BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint) {

        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;

        int w = img.getWidth();
        int h = img.getHeight();

        do {
            if (w > targetWidth) {
                w /= 4;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (h > targetHeight) {
                h /= 4;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
}
