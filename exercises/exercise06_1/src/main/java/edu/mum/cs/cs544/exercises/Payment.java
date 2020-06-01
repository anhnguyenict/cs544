package edu.mum.cs.cs544.exercises;

import javax.persistence.Embeddable;

@Embeddable
public class Payment {
    private String paydate;
    private double amount;

    public Payment() {

    }

    public Payment(String paydate, double amount) {
        super();
        this.paydate = paydate;
        this.amount = amount;
    }

    public String getPaydate() {
        return paydate;
    }

    public double getAmount() {
        return amount;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
