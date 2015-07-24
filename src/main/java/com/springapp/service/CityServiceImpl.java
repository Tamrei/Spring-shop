package com.springapp.service;

import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Address;
import com.springapp.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@SuppressWarnings("unchecked")
public class CityServiceImpl implements CityService {

    @Autowired
    private GenericDAO cityDAO;

    @Override
    @Transactional
    public void addSupportedCity(City city) {
        cityDAO.add(city);
    }

    @Override
    @Transactional
    public List<City> getAllSupportedCities() {
        return cityDAO.getAll();
    }

    @Override
    @Transactional
    public City getCity(long id) {
        return (City) cityDAO.get(id);
    }

    @Override
    public void updateCity(City city) {
        cityDAO.update(city);
    }

    @Override
    public void deleteCity(long id) {
        cityDAO.delete(id);
    }
}
