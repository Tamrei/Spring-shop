package com.springapp.controller;

import com.springapp.anotation.ActiveUser;
import com.springapp.model.Item;
import com.springapp.model.site.HomePageImage;
import com.springapp.service.CartService;
import com.springapp.service.ItemService;
import com.springapp.service.site.HomePageImageService;
import com.springapp.service.site.HomePageImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@SessionAttributes("cartCount")
public class ShopController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "shop")
    public String itemsList(ModelMap modelMap) {
        modelMap.put("item", new Item());
        modelMap.put("items", itemService.getAllItems());

        return "shop";
    }

    @RequestMapping(value = "shop/{itemID}", method = RequestMethod.POST)
    public String putItemInTheCart(@PathVariable Integer itemID, @ActiveUser User activeUser,
                                   @RequestParam("amount") long amount) {
        itemService.putItemInCart(itemID, activeUser.getUsername(), amount);

        return "redirect:/shop";
    }

    @RequestMapping(value = "shop/img/{itemID}")
    @Transactional
    public void displayImage(@PathVariable Integer itemID, HttpServletResponse response) {
        Item item = itemService.getItem(itemID);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        try {
            response.getOutputStream().write(item.getImage());
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "shop/update/{itemID}", method = RequestMethod.POST)
    public String updateItem(@ModelAttribute("item") @Valid Item item,
                             @RequestParam("file") MultipartFile file) {
        try {
            item.setImage(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemService.updateItem(item);

        return "redirect:/shop";
    }

    @Autowired
    private HomePageImageService homePageImageService;

    @ModelAttribute(value = "sliderImages")
    public void getSliderImages(ModelMap modelMap) {
        modelMap.addAttribute("sliderImages", homePageImageService.getAllHomePageImages());
    }

    @RequestMapping(value = "shop/slider/{id}")
    @Transactional
    public void displaySliderImage(@PathVariable Integer id, HttpServletResponse response) {
        HomePageImage homePageImage = homePageImageService.getHomePageImage(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        try {
            response.getOutputStream().write(homePageImage.getImage());
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
