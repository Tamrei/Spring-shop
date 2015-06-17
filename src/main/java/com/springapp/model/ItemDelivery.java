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

    @Column(name="itemsLeft")
    private long itemsLeft;

    @ManyToOne
    @JoinColumn(name = "itemID", insertable = false, updatable = false)
    private Item item;

    //@Temporal(TemporalType.TIME)
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

    public long getItemsLeft() {
        return itemsLeft;
    }

    public void setItemsLeft(long itemsLeft) {
        this.itemsLeft = itemsLeft;
    }
}
