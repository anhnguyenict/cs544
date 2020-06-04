package edu.miu.cs.cs544.examples.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
public class Country {
    @Id
    @Column(name = "COUNTRY_ID")
    private Integer id;
    
    @Column(name = "COUNTRY")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
