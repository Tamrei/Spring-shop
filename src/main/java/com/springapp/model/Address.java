package com.springapp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "address")
@Proxy(lazy = false)
//@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="yourEntityCache")
public class Address {
    @Id
    @GenericGenerator(name = "test", strategy = "increment")
    @GeneratedValue(generator = "test")
    @Column(name = "addressID")
    private long addressID;

    @Column(name = "ownerUsername")
    private String ownerUsername;

    @Column(name = "city")
    @Size(min=1, max=20, message = "City name can't be empty!")
    private String city;

    @Column(name = "street")
    @Size(min=1, max=20, message = "Street name can't be empty!")
    private String street;

    @OneToMany(mappedBy = "address")
    private Set<Purchase> purchases;

    public Address() {
    }

    public Address(long addressID, String ownerUsername, String city, String street) {
        this.addressID = addressID;
        this.ownerUsername = ownerUsername;
        this.city = city;
        this.street = street;
    }

    public Address(String ownerUsername, String city, String street) {
        this.ownerUsername = ownerUsername;
        this.city = city;
        this.street = street;
    }

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public long getAddressID() {
        return addressID;
    }

    public void setAddressID(long addressID) {
        this.addressID = addressID;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (addressID != address.addressID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (addressID ^ (addressID >>> 32));
    }
}
