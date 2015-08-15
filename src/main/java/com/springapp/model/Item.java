package com.springapp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "item")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="yourEntityCache")
public class Item implements Serializable {
    @Id
    @GenericGenerator(name = "id_generator", strategy = "increment")
    @GeneratedValue(generator = "id_generator")
    @Column(name = "itemID")
    private long itemID;

    @Column(name = "itemName")
    @Size(min=1, max=20, message = "Invalid item name")
    private String itemName;
    
    @Column(name = "type")
    @Size(min=1, max=15, message = "Invalid type")
    private String type;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "price")
    private float price;

    @Column(name = "leftOnStore")
    private long leftOnStore = 0;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<ItemDelivery> itemDeliveries;

    public Item() {
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getLeftOnStore() {
        return leftOnStore;
    }

    public void setLeftOnStore(long leftOnStore) {
        this.leftOnStore = leftOnStore;
    }

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<ItemDelivery> getItemDeliveries() {
        return itemDeliveries;
    }

    public void setItemDeliveries(Set<ItemDelivery> itemDeliveries) {
        this.itemDeliveries = itemDeliveries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (itemID != item.itemID) return false;
        if (!itemName.equals(item.itemName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (itemID ^ (itemID >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID=" + itemID +
                ", itemName='" + itemName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", leftOnStore=" + leftOnStore +
                ", available=" + available +
                '}';
    }
}
