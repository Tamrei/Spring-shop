package com.springapp.service;

import com.springapp.dao.generic.GenericDAO;
import com.springapp.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private GenericDAO addressDAO;

    @Override
    @Transactional
    public void addAddress(Address address) {
        addressDAO.add(address);
    }

    @Override
    @Transactional
    public List<Address> getAllAddresses() {
        return addressDAO.getAll();
    }

    @Override
    @Transactional
    public Address getAddress(long id) {
        return (Address) addressDAO.get(id);
    }

    @Override
    @Transactional
    public void updateAddress(Address address) {
        addressDAO.update(address);
    }

    @Override
    @Transactional
    public void deleteAddress(long id) {
        addressDAO.delete(id);
    }


}
