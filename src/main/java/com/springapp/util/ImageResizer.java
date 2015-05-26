package com.springapp.util;

import com.springapp.exceptions.ImageFormatException;

import java.io.IOException;


public interface ImageResizer {

    public byte[] resizeImage(byte[] image, int width, int height) throws IOException;
}
