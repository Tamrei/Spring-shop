package com.springapp.service.site;


import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Address;
import com.springapp.model.site.HomePageImage;
import com.springapp.util.ImageResizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@SuppressWarnings("unchecked")
@Transactional
public class HomePageImageServiceImpl implements HomePageImageService {

    @Autowired
    private GenericDAO homePageImageDAO;

    @Override
    public void addHomePageImage(HomePageImage homePageImage, MultipartFile image, int width, int height) throws IOException {
        homePageImage.setImage(ImageResizer.resizeImage(image.getBytes(), width, height));
        homePageImageDAO.add(homePageImage);
    }

    @Override
    public List<Address> getAllHomePageImages() {
        return homePageImageDAO.getAll();
    }

    @Override
    public HomePageImage getHomePageImage(long id) {
        return (HomePageImage) homePageImageDAO.get(id);
    }

    @Override
    public void updateHomePageImage(HomePageImage homePageImage) {
        homePageImageDAO.update(homePageImage);
    }

    @Override
    public void deleteHomePageImage(long id) {
        homePageImageDAO.delete(id);
    }


}
