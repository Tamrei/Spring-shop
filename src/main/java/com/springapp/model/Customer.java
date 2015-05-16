package com.springapp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "customer")
//@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="yourEntityCache")
public class Customer implements Serializable {
    @Id
    @GenericGenerator(name = "test", strategy = "increment")
    @GeneratedValue(generator = "test")
    @Column(name="customerID")
    private long id;

    @Column(name="username")
    @Size(min=1, max=20, message = "Invalid username")
    private String username;

    @Column(name="password")
    @Size(min=1, max=20, message = "Invalid password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    @OneToMany(mappedBy = "customer")
    private Set<Purchase> purchases;

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Customer() {
        role = UserRoles.USER;
        enabled = true;
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer(String username, String password, UserRoles role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (!username.equals(customer.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                '}';
    }
}

