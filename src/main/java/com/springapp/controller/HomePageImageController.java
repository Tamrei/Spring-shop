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
@SessionAttributes(value = {"homePageImages"})
public class HomePageImageController {

    @Autowired
    private HomePageImageService homePageImageService;

    @RequestMapping(value = "carouselController")
    public String carouselController(ModelMap modelMap) {
        modelMap.addAttribute("homePageImages", homePageImageService.getAllHomePageImages());
        return "carouselController";
    }

    @RequestMapping(value = "homePageImages")
    public
    //@ResponseBody
    void HomePageImagesList(ModelMap modelMap) {
        System.out.println("GET IMAGES FROM CAROUSEL!!! REQUEST!!! SIZE IS : " + homePageImageService.getAllHomePageImages().size());
        modelMap.addAttribute("homePageImages", homePageImageService.getAllHomePageImages());
    }

    @RequestMapping(value = "carouselController/addNewImage", method = RequestMethod.POST)
    public ModelAndView addNewImageToCarousel(@ModelAttribute("item") @Valid HomePageImage homePageImage,
                                              @RequestParam("file") MultipartFile file) {
        try {
            homePageImageService.addHomePageImage(homePageImage, file, 1500, 430);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return new ModelAndView("carouselController", "notAnImage", "Woops! seams like its not an image!");
        }

        System.out.println("DISPLAY IMAGE ADD NEW| ID:" + homePageImage.getId());

        return new ModelAndView("redirect:/carouselController");
    }

    @RequestMapping(value = "homePageImage/img/{id}")
    @Transactional
    public void displayImage(@PathVariable Integer id, HttpServletResponse response) {
        System.out.println("DISPLAY IMAGE ID: " + id);
        HomePageImage homePageImage = homePageImageService.getHomePageImage(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");

        try {
            response.getOutputStream().write(homePageImage.getImage());
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DISPLAY IMAGE ERROR ID: " + id);
        }
    }


    @RequestMapping(value = "carouselController/delete/{id}", method = RequestMethod.DELETE)
    public String deleteImage(@PathVariable Integer id) {
        System.out.println("DISPLAY IMAGE DELETE ID:" + id);
        homePageImageService.deleteHomePageImage(id);

        return "redirect:/carouselController";
    }

    // delete ajax
    /*@RequestMapping(value = "carouselController/delete", method = RequestMethod.POST)
    public void deleteImage(@RequestParam Integer id) {
        homePageImageService.deleteHomePageImage(id);
    }*/
}
