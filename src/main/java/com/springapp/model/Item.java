package com.springapp.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

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

    public Item() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
//        result = 31 * result + itemName.hashCode();
        return result;
    }
}
