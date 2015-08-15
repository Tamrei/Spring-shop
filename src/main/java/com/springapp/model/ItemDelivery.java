package com.springapp.model;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "itemdelivery")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="yourEntityCache")
public class ItemDelivery implements Serializable {
    @Id
    @GenericGenerator(name = "id_generator", strategy = "increment")
    @GeneratedValue(generator = "id_generator")
    @Column(name = "itemDeliveryID")
    private long itemDeliveryID;

    @Column(name = "itemID")
    private long itemID;

    @Column(name="itemQuantity")
    private long itemQuantity;

    @ManyToOne
    @JoinColumn(name = "itemID", insertable = false, updatable = false)
    private Item item;

    @Column(name = "dateOfDelivery")
    private Date dateOfDelivery;

    public Date getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(Date dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public ItemDelivery() {
        this.dateOfDelivery = Calendar.getInstance().getTime();
    }

    public long getItemDeliveryID() {
        return itemDeliveryID;
    }

    public void setItemDeliveryID(long itemDeliveryID) {
        this.itemDeliveryID = itemDeliveryID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDelivery)) return false;

        ItemDelivery that = (ItemDelivery) o;

        if (itemDeliveryID != that.itemDeliveryID) return false;
        if (itemID != that.itemID) return false;
        if (itemQuantity != that.itemQuantity) return false;
        if (dateOfDelivery != null ? !dateOfDelivery.equals(that.dateOfDelivery) : that.dateOfDelivery != null)
            return false;
        if (item != null ? !item.equals(that.item) : that.item != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (itemDeliveryID ^ (itemDeliveryID >>> 32));
        result = 31 * result + (int) (itemID ^ (itemID >>> 32));
        result = 31 * result + (int) (itemQuantity ^ (itemQuantity >>> 32));
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (dateOfDelivery != null ? dateOfDelivery.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemDelivery{" +
                "itemDeliveryID=" + itemDeliveryID +
                ", itemID=" + itemID +
                ", itemQuantity=" + itemQuantity +
                ", dateOfDelivery=" + dateOfDelivery +
                '}';
    }
}
