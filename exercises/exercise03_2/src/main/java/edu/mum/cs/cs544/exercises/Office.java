package edu.mum.cs.cs544.exercises;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Office {
    @Id
    private int roomnumber;
    private String building;
    @OneToMany(mappedBy = "office")
    private List<Employee> employees = new ArrayList();
    
    public Office() {
        
    }

    public Office(int roomnumber, String building) {
        this.roomnumber = roomnumber;
        this.building = building;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(int roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
