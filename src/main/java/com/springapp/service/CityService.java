package com.springapp.service;

import com.springapp.model.Address;
import com.springapp.model.City;

import java.util.List;


public interface CityService {
    public void addSupportedCity(City city);
    public List<City> getAllSupportedCities();
    public City getCity(long id);
    public void updateCity(City city);
    public void deleteCity(long id);
}
