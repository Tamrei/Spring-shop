package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.Item;
import com.springapp.model.site.HomePageImage;
import com.springapp.service.site.HomePageImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@Controller
public class HomePageImageController {

    @Autowired
    private HomePageImageService homePageImageService;

    @RequestMapping(value = "carouselController")
    public String HomePageImagesList(@ActiveUser User activeUser, ModelMap modelMap) {
        modelMap.addAttribute("homePageImages", homePageImageService.getAllHomePageImages());

        return "carouselController";
    }

    @RequestMapping(value="carouselController/addNewImage", method = RequestMethod.POST)
    public ModelAndView addNewImage(@ModelAttribute("item") @Valid HomePageImage homePageImage,
                             @RequestParam("file") MultipartFile file) {
        try {
            homePageImage.setImage(file.getBytes());
            homePageImageService.addHomePageImage(homePageImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            return new ModelAndView("carouselController", "notAnImage", "Woops! seams like its not an image!");
        }

        return new ModelAndView("redirect:/carouselController");
    }

    @RequestMapping(value = "carouselController/img/{id}")
    @Transactional
    public void displayImage(@PathVariable Integer id, HttpServletResponse response) {
        HomePageImage homePageImage = homePageImageService.getHomePageImage(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        try {
            response.getOutputStream().write(homePageImage.getImage());
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "carouselController/delete/{id}")
    public String deleteImage(@PathVariable Integer id) {
        homePageImageService.deleteHomePageImage(id);

        return "redirect:/carouselController";
    }
}
