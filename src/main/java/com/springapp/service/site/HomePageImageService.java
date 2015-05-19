package com.springapp.service.site;


import com.springapp.model.Address;
import com.springapp.model.site.HomePageImage;

import java.io.IOException;
import java.util.List;

public interface HomePageImageService {
    public void addHomePageImage(HomePageImage homePageImage) throws IOException;
    public List<Address> getAllHomePageImages();
    public HomePageImage getHomePageImage(long id);
    public void updateHomePageImage(HomePageImage homePageImage);
    public void deleteHomePageImage(long id);
}
