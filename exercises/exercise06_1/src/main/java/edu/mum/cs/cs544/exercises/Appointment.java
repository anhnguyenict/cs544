package edu.mum.cs.cs544.exercises;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String appdate;
    @ManyToOne
    private Patient patient;
    @Embedded
    private Payment payment;
    @ManyToOne
    private Doctor doctor;

    public Appointment() {

    }

    public Appointment(String appdate, Patient patient, Payment payment, Doctor doctor) {
        super();
        this.appdate = appdate;
        this.patient = patient;
        this.payment = payment;
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public String getAppdate() {
        return appdate;
    }

    public Patient getPatient() {
        return patient;
    }

    public Payment getPayment() {
        return payment;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
