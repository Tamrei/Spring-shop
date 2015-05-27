package com.springapp.model.site;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "homepageimage")
//@Proxy(lazy = false)
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE, region="yourEntityCache")
public class HomePageImage implements Serializable {

    @Id
    @GenericGenerator(name = "id_generator", strategy = "increment")
    @GeneratedValue(generator = "id_generator")
    @Column(name = "id")
    private long id;

    @Column(name = "image")
    private byte[] image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HomePageImage)) return false;

        HomePageImage that = (HomePageImage) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
