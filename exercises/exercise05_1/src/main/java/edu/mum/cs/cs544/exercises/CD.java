package edu.mum.cs.cs544.exercises;

import javax.persistence.Entity;

@Entity
public class CD extends Product {
    private String artist;

    public CD() {

    }

    public CD(String artist) {
        super();
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    };
}
