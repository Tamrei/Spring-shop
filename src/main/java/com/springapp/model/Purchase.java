package com.springapp.model;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "purchase")
//@Cache(usage= CacheConcurrencyStrategy.READ_ONLY, region="yourEntityCache")
public class Purchase implements Serializable {
    @Id
    @GenericGenerator(name = "test", strategy = "increment")
    @GeneratedValue(generator = "test")
    @Column(name = "purchaseID")
    private long purchaseID;

    @Column(name = "ownerUsername")
    private String ownerUsername;

    @Column(name = "price")
    private double price;

    @Column(name = "addressID")
    private long addressID;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @OneToMany(mappedBy = "purchase")
    private Set<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "ownerUsername", referencedColumnName = "username", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "addressID", insertable = false, updatable = false)
    private Address address;

    public Purchase() {
    }

    public Purchase(Address address) {
        this.address = address;
        this.setAddressID(address.getAddressID());
        this.status = PurchaseStatus.PROCESSED;
        this.setOwnerUsername(address.getOwnerUsername());
    }

    public long getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(long purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAddressID() {
        return addressID;
    }

    public void setAddressID(long addressID) {
        this.addressID = addressID;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        this.setAddressID(address.getAddressID());
        this.status = PurchaseStatus.PROCESSED;
        this.setOwnerUsername(address.getOwnerUsername());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;

        Purchase purchase = (Purchase) o;

        if (addressID != purchase.addressID) return false;
        if (purchaseID != purchase.purchaseID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (purchaseID ^ (purchaseID >>> 32));
        result = 31 * result + (int) (addressID ^ (addressID >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseID=" + purchaseID +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", price=" + price +
                ", addressID=" + addressID +
                ", status=" + status +
                '}';
    }
}
