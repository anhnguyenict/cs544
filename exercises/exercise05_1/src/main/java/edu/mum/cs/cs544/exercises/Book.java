package edu.mum.cs.cs544.exercises;

import javax.persistence.Entity;

@Entity
public class Book extends Product {
    private String title;

    public Book() {

    }

    public Book(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
