package com.springapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * This entity represents available
 * city's for purchase delivery
 */
@Entity
@Table(name = "city")
public class City {

    @Id
    @GenericGenerator(name = "test", strategy = "increment")
    @GeneratedValue(generator = "test")
    @Column(name = "id")
    private long id;

    @Column(name="cityName", unique=true)
    private String cityName;

    public City() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (!cityName.equals(city.cityName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + cityName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.cityName;
    }
}
