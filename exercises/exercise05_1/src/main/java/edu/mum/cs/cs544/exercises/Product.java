package edu.mum.cs.cs544.exercises;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DiscriminatorColumn(name = "productType")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String descripton;

    @Column(insertable = false, updatable = false)
    private String productType;

    public Product() {

    }

    public Product(String name, String descripton) {
        this.name = name;
        this.descripton = descripton;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
