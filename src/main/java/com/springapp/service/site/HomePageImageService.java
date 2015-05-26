package com.springapp.service.site;


import com.springapp.exceptions.ImageFormatException;
import com.springapp.model.Address;
import com.springapp.model.site.HomePageImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HomePageImageService {
    public void addHomePageImage(HomePageImage homePageImage, MultipartFile image, int width, int height) throws IOException;
    public List<Address> getAllHomePageImages();
    public HomePageImage getHomePageImage(long id);
    public void updateHomePageImage(HomePageImage homePageImage);
    public void deleteHomePageImage(long id);
}
