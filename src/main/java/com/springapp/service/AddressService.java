package com.springapp.service;

import com.springapp.model.Address;

import java.util.List;

public interface AddressService {
    public void addAddress(Address address);
    public List<Address> getAllAddresses();
    public Address getAddress(long id);
    public void updateAddress(Address address);
    public void deleteAddress(long id);
}
