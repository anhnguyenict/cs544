package edu.miu.cs.cs544.examples.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
public class City {
    @Id
    @Column(name = "CITY_ID")
    private Integer id;
    
    @Column(name = "CITY")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
