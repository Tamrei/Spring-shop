package com.springapp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "cart")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="yourEntityCache")
public class Cart implements Serializable {
    @Id
    @GenericGenerator(name = "test", strategy = "increment")
    @GeneratedValue(generator = "test")
    @Column(name="cartID")
    private long cartID;

    @Column(name="itemID")
    private long itemID;

    @Column(name="ownerUsername")
    private String ownerUsername;

    /**
     * if addressID null purchase
     *    purchase not ordered
     * else
     *    purchase ordered and waiting for delivery
     */
    @Column(name="purchaseID")
    private Long purchaseID;

    @Column(name="amount")
    private long amount;

    @ManyToOne
    @JoinColumn(name = "ownerUsername", referencedColumnName = "username", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "purchaseID", insertable = false, updatable = false)
    private Purchase purchase;

    public Cart() {
    }

    public Cart(long itemID, String ownerUsername, long amount) {
        this.itemID = itemID;
        this.ownerUsername = ownerUsername;
        this.amount = amount;
    }

    public long getCartID() {
        return cartID;
    }

    public void setCartID(long cartID) {
        this.cartID = cartID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Long getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(Long orderID) {
        this.purchaseID = orderID;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;

        Cart cart = (Cart) o;

        if (cartID != cart.cartID) return false;
        if (itemID != cart.itemID) return false;
        if (ownerUsername != null ? !ownerUsername.equals(cart.ownerUsername) : cart.ownerUsername != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (cartID ^ (cartID >>> 32));
        result = 31 * result + (int) (itemID ^ (itemID >>> 32));
        result = 31 * result + (ownerUsername != null ? ownerUsername.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID=" + cartID +
                ", itemID=" + itemID +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", purchaseID=" + purchaseID +
                ", amount=" + amount +
                '}';
    }
}
